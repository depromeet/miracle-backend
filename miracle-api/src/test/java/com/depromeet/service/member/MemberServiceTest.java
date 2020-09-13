package com.depromeet.service.member;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.AlarmCreator;
import com.depromeet.domain.alarm.AlarmRepository;
import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleCreator;
import com.depromeet.domain.alarm.AlarmScheduleRepository;
import com.depromeet.domain.alarm.AlarmType;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.member.AuthProvider;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberGoal;
import com.depromeet.domain.member.MemberGoalCreator;
import com.depromeet.domain.member.MemberGoalRepository;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.domain.member.MemberType;
import com.depromeet.domain.member.ProfileIcon;
import com.depromeet.domain.member.deleted.DeletedMember;
import com.depromeet.domain.member.deleted.DeletedMemberGoal;
import com.depromeet.domain.member.deleted.DeletedMemberGoalRepository;
import com.depromeet.domain.member.deleted.DeletedMemberRepository;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberGoalsRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import com.deprommet.exception.ConflictException;
import com.deprommet.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberGoalRepository memberGoalRepository;

    @Autowired
    private AlarmScheduleRepository alarmScheduleRepository;

    @Autowired
    private DeletedMemberRepository deletedMemberRepository;

    @Autowired
    private DeletedMemberGoalRepository deletedMemberGoalRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    private Member member;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        alarmRepository.deleteAllInBatch();
        alarmScheduleRepository.deleteAllInBatch();
        deletedMemberGoalRepository.deleteAllInBatch();
        deletedMemberRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setUpMemberSample() {
        member = MemberCreator.create("will.seungho@gmail.com", "강승호", ProfileIcon.RED);
    }

    @Test
    void 새로운_멤버가_회원가입_하면_해당_멤버_정보가_저장된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        ProfileIcon profileIcon = ProfileIcon.RED;
        LocalTime wakeUpTime = LocalTime.of(10, 0);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .goals(Collections.emptyList())
            .wakeUpTime(wakeUpTime)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), email, name, profileIcon, wakeUpTime);
    }

    @Test
    void 새로운_멤버가_회원가입시_멤버의_목표정보도_저장된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        List<Category> goals = Arrays.asList(Category.EXERCISE, Category.READING);
        ProfileIcon profileIcon = ProfileIcon.BLUE;

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .goals(goals)
            .profileIcon(profileIcon)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).hasSize(2);
        assertThat(memberGoals).extracting("category").containsExactly(Category.EXERCISE, Category.READING);
    }

    @Test
    void 새로운_멤버가_회원가입시_아무런_목표도_선택하지_않을수_있다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        List<Category> goals = Collections.emptyList();
        ProfileIcon profileIcon = ProfileIcon.BLUE;

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .goals(goals)
            .profileIcon(profileIcon)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).isEmpty();
    }

    @Test
    void 회원가입시_이미_존재하는_이메일인경우_에러가_발생한다() {
        // given
        memberRepository.save(member);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(member.getEmail())
            .build();

        // when & then
        assertThatThrownBy(() -> {
            memberService.signUpMember(request);
        }).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원가입시_입력받은_기상시간으로_기본_알림스케쥴이_생성된다() {
        // given
        LocalTime wakeUpTime = LocalTime.of(8, 0);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email("will.seungho@gmail.com")
            .name("강승호")
            .wakeUpTime(wakeUpTime)
            .goals(Collections.emptyList())
            .profileIcon(ProfileIcon.BLUE)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<AlarmSchedule> alarmSchedules = alarmScheduleRepository.findAll();
        assertThat(alarmSchedules).hasSize(1);
        assertThat(alarmSchedules.get(0).getType()).isEqualTo(AlarmType.WAKE_UP);

        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).hasSize(7);
        assertThat(alarms).extracting("reminderTime").contains(LocalTime.of(8, 0));
    }

    @Test
    void 멤버의_회원정보를_변경한다() {
        // given
        String name = "kangseungho";
        ProfileIcon profileIcon = ProfileIcon.BLUE;
        LocalTime wakeUpTime = LocalTime.of(12, 0);

        memberRepository.save(member);

        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name(name)
            .profileIcon(profileIcon)
            .wakeUpTime(wakeUpTime)
            .build();

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), member.getEmail(), name, profileIcon, wakeUpTime);
    }

    @Test
    void 멤버의_회원정보를_변경할때_존재하지_않는_멤버인경우_에러가_발생한다() {
        // given
        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name("name")
            .build();

        // when & then
        assertThatThrownBy(() -> {
            memberService.updateMemberInfo(request, 999L);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 멤버의_기상_시간을_변경하면_기존의_알림스케쥴이_삭제되고_새로운_알람스케쥴이_생성된다() {
        // given
        String name = "kangseungho";
        ProfileIcon profileIcon = ProfileIcon.BLUE;
        LocalTime wakeUpTime = LocalTime.of(12, 0);

        memberRepository.save(member);

        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(member.getId(), AlarmType.WAKE_UP, "기본 기상 알람");
        alarmSchedule.addAlarms(Collections.singletonList(AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(1, 0))));
        alarmScheduleRepository.save(alarmSchedule);

        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name(name)
            .profileIcon(profileIcon)
            .wakeUpTime(wakeUpTime)
            .build();

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<AlarmSchedule> alarmSchedules = alarmScheduleRepository.findAll();
        assertThat(alarmSchedules).hasSize(1);
        assertThat(alarmSchedules.get(0).getId()).isNotEqualTo(alarmSchedule.getId());
        assertThat(alarmSchedules.get(0).getType()).isEqualTo(AlarmType.WAKE_UP);

        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).hasSize(7);
        assertThat(alarms).extracting("reminderTime").contains(wakeUpTime);
    }

    @Test
    void 멤버의_목표_정보를_변경한다() {
        // given
        memberRepository.save(member);

        List<Category> categories = Arrays.asList(Category.MEDITATION, Category.READING);

        UpdateMemberGoalsRequest request = UpdateMemberGoalsRequest.testInstance(categories);

        // when
        memberService.updateMemberGoals(request, member.getId());

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).hasSize(2);
        assertThat(memberGoals).extracting("category").containsExactly(Category.MEDITATION, Category.READING);
    }

    @Test
    void 멤버의_목표_정보를_변경할때_아무런_목표도_없을_수있다() {
        // given
        memberRepository.save(member);

        UpdateMemberGoalsRequest request = UpdateMemberGoalsRequest.testInstance(Collections.emptyList());

        // when
        memberService.updateMemberGoals(request, member.getId());

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).isEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "source_load_my_info_ShouldSuccess")
    void 내정보를_불러온다(Member member) {
        // given
        memberRepository.save(member);

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertMemberInfoResponse(response, member.getEmail(), member.getName(), member.getProfileIcon());
    }

    static Stream<Arguments> source_load_my_info_ShouldSuccess() {
        return Stream.of(
            Arguments.of(MemberCreator.create("will.seungho@gmail.com", "강승호", ProfileIcon.BLUE)),
            Arguments.of(MemberCreator.create("ksh980212@gmail.com", "호승강", ProfileIcon.RED))
        );
    }

    @Test
    void 내정보를_불러올때_존재하지_않는_멤버인경우_에러가_발생한다() {
        // when & then
        assertThatThrownBy(() -> {
            memberService.getMemberInfo(999L);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원탈퇴시_회원정보를_삭제한다() {
        // given
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).isEmpty();
    }

    @Test
    void 회원탈퇴시_회원의_목표도_삭제된다() {
        // given
        member.addMemberGoals(Collections.singletonList(MemberGoalCreator.create(Category.READING)));
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).isEmpty();
    }

    @Test
    void 회원탈퇴시_DeletedMember_에_보관된다() {
        // given
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<DeletedMember> deletedMembers = deletedMemberRepository.findAll();
        assertThat(deletedMembers).hasSize(1);
        assertDeletedMemberInfo(deletedMembers.get(0), member.getId(), member.getEmail(), member.getName(), member.getProfileIcon(), member.getProvider(), member.getType());
    }

    @Test
    void 회원탈퇴시_회원의_목표도_백업된다() {
        // given
        MemberGoal memberGoal = MemberGoalCreator.create(Category.READING);
        member.addMemberGoals(Collections.singletonList(memberGoal));
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<DeletedMemberGoal> deletedMemberGoals = deletedMemberGoalRepository.findAll();
        assertThat(deletedMemberGoals).hasSize(1);
        assertThat(deletedMemberGoals.get(0).getCategory()).isEqualTo(memberGoal.getCategory());
    }

    private void assertMemberInfoResponse(MemberInfoResponse response, String email, String name, ProfileIcon profileIcon) {
        assertAll(
            () -> assertThat(response.getEmail()).isEqualTo(email),
            () -> assertThat(response.getName()).isEqualTo(name),
            () -> assertThat(response.getProfileIcon()).isEqualTo(profileIcon)
        );
    }

    private void assertMemberInfo(Member member, String email, String name, ProfileIcon profileIcon, LocalTime wakeUpTime) {
        assertAll(
            () -> assertThat(member.getEmail()).isEqualTo(email),
            () -> assertThat(member.getName()).isEqualTo(name),
            () -> assertThat(member.getProfileIcon()).isEqualTo(profileIcon),
            () -> assertThat(member.getWakeUpTime()).isEqualTo(wakeUpTime)
        );
    }

    private void assertDeletedMemberInfo(DeletedMember deletedMember, Long memberId, String email, String name, ProfileIcon profileIcon, AuthProvider provider, MemberType type) {
        assertAll(
            () -> assertThat(deletedMember.getBackupId()).isEqualTo(memberId),
            () -> assertThat(deletedMember.getEmail()).isEqualTo(email),
            () -> assertThat(deletedMember.getName()).isEqualTo(name),
            () -> assertThat(deletedMember.getProfileIcon()).isEqualTo(profileIcon),
            () -> assertThat(deletedMember.getProvider()).isEqualTo(provider),
            () -> assertThat(deletedMember.getType()).isEqualTo(type)
        );
    }

}
