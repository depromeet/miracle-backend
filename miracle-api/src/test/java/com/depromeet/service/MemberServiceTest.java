package com.depromeet.service;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.service.member.MemberService;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 새로운_멤버가_회원가입한다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "kangseungho";
        String profileUrl = "http://profileUrl.test.com";

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMemberInfo(members.get(0), email, name, profileUrl);
    }

    @Test
    void 회원가입시_이미_존재하는_이메일인경우() {
        // given
        String email = "will.seungho.@gmail.com";
        memberRepository.save(MemberCreator.create(email));

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name("강승호")
            .profileUrl("test")
            .build();

        // when & then
        assertThatThrownBy(() -> {
            memberService.signUpMember(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMemberInfo(Member member, String email, String name, String profileUrl) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}
