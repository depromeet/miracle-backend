package com.depromeet.service.member;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.AlarmRepository;
import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleRepository;
import com.depromeet.domain.alarm.AlarmType;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberGoal;
import com.depromeet.domain.member.MemberGoalRepository;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.domain.member.ProfileIcon;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberGoalsRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private AlarmRepository alarmRepository;

    private Member member;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        alarmRepository.deleteAllInBatch();
        alarmScheduleRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setUpMemberSample() {
        member = MemberCreator.create("will.seungho@gmail.com", "강승호", ProfileIcon.RED, Collections.singletonList(Category.EXERCISE));
    }

    @Test
    void 새로운_멤버가_회원가입한다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        ProfileIcon profileIcon = ProfileIcon.RED;

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), email, name, profileIcon);
    }

    @Test
    void 새로운_멤버가_회원가입시_멤버의_목표정보도_저장된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        List<Category> goals = Arrays.asList(Category.EXERCISE, Category.READING);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .goals(goals)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).hasSize(2);
        assertThat(memberGoals).extracting("category").containsExactly(Category.EXERCISE, Category.READING);
    }

    @Test
    void 새로운_멤버가_회원가입시_멤버의_목표를_선택하지_않을수_있다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        List<Category> goals = Collections.emptyList();

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .goals(goals)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).isEmpty();
    }

    @Test
    void 회원가입시_이미_존재하는_이메일인경우() {
        // given
        memberRepository.save(member);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(member.getEmail())
            .build();

        // when & then
        assertThatThrownBy(() -> {
            memberService.signUpMember(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원가입시_입력받은_기상시간으로_기본_알림시간이_생성된다() {
        // given
        LocalTime wakeUpTime = LocalTime.of(8, 0);

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email("will.seungho@gmail.com")
            .name("강승호")
            .wakeUpTime(wakeUpTime)
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

        memberRepository.save(member);

        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name(name)
            .profileIcon(profileIcon)
            .build();

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), member.getEmail(), name, profileIcon);
    }

    @Test
    void 멤버의_회원정보를_변경한다_존재하지_않는_멤버인경우() {
        // given
        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name("name")
            .build();

        // when & then
        assertThatThrownBy(() -> {
            memberService.updateMemberInfo(request, 999L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 멤버의_목표정보를_변경한다() {
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
    void 멤버의_목표정보를_변경한다_어떤_목표도_없을_수있다() {
        // given
        memberRepository.save(member);

        UpdateMemberGoalsRequest request = UpdateMemberGoalsRequest.testInstance(Collections.emptyList());

        // when
        memberService.updateMemberGoals(request, member.getId());

        // then
        List<MemberGoal> memberGoals = memberGoalRepository.findAll();
        assertThat(memberGoals).isEmpty();
    }

    @Test
    void 내정보를_불러온다() {
        // given
        memberRepository.save(member);

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertMemberInfoResponse(response, member.getEmail(), member.getName(), member.getProfileIcon());
    }

    @Test
    void 내정보를_불러온다_존재하지_않는_멤버인경우() {
        // when & then
        assertThatThrownBy(() -> {
            memberService.getMemberInfo(999L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMemberInfoResponse(MemberInfoResponse response, String email, String name, ProfileIcon profileIcon) {
        assertAll(
            () -> assertThat(response.getEmail()).isEqualTo(email),
            () -> assertThat(response.getName()).isEqualTo(name),
            () -> assertThat(response.getProfileIcon()).isEqualTo(profileIcon)
        );
    }

    private void assertMemberInfo(Member member, String email, String name, ProfileIcon profileIcon) {
        assertAll(
            () -> assertThat(member.getEmail()).isEqualTo(email),
            () -> assertThat(member.getName()).isEqualTo(name),
            () -> assertThat(member.getProfileIcon()).isEqualTo(profileIcon)
        );
    }

}
