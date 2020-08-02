package com.depromeet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void test_findMemberByEmail() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "will";
        String profileUrl = "profileUrl";
        String phoneNumber = "010-1234-1234";

        memberRepository.save(MemberCreator.create(email, name, profileUrl, phoneNumber));

        // when
        Member member = memberRepository.findMemberByEmail(email);

        // then
        assertMemberInfo(member, email, name, profileUrl, phoneNumber);
    }

    private void assertMemberInfo(Member member, String email, String name, String profileUrl, String phoneNumber) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber);
    }

}
