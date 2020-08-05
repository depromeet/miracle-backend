package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @BeforeEach
    void setUpMemberSample() {
        member = MemberCreator.create("will.seungho@gmail.com", "강승호");
    }

    @Test
    void 새로운_멤버가_회원가입한다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), email, name);
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
    void 멤버의_회원정보를_변경한다() {
        // given
        String name = "kangseungho";

        memberRepository.save(member);

        UpdateMemberInfoRequest request = UpdateMemberInfoRequest.testBuilder()
            .name(name)
            .build();

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), member.getEmail(), name);
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
    void 내정보를_불러온다() {
        // given
        memberRepository.save(member);

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertMemberInfoResponse(response, member.getEmail(), member.getName());
    }

    @Test
    void 내정보를_불러온다_존재하지_않는_멤버인경우() {
        // when & then
        assertThatThrownBy(() -> {
            memberService.getMemberInfo(999L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMemberInfoResponse(MemberInfoResponse response, String email, String name) {
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getName()).isEqualTo(name);
    }

    private void assertMemberInfo(Member member, String email, String name) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
    }

}
