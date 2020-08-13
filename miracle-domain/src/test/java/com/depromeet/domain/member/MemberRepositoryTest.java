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
        ProfileIcon profileIcon = ProfileIcon.BLUE;

        memberRepository.save(MemberCreator.create(email, name, profileIcon));

        // when
        Member member = memberRepository.findMemberByEmail(email);

        // then
        assertMemberInfo(member, email, name, profileIcon);
    }

    private void assertMemberInfo(Member member, String email, String name, ProfileIcon profileIcon) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileIcon()).isEqualTo(profileIcon);
    }

}
