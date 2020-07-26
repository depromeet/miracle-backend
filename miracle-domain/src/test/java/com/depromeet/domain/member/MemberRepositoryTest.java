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

        memberRepository.save(MemberCreator.create(email, name, profileUrl));

        // when
        Member member = memberRepository.findMemberByEmail(email);

        // then
        assertMemberInfo(member, email, name, profileUrl);
    }

    private void assertMemberInfo(Member member, String email, String name, String profileUrl) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}
