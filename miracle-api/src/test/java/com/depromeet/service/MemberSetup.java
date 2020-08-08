package com.depromeet.service;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class MemberSetup {

    @Autowired
    private MemberRepository memberRepository;

    protected static Long memberId;

    @BeforeEach
    void setup() {
        Member member = MemberCreator.create("will.seungho@gmail.com");
        memberRepository.save(member);
        memberId = member.getId();
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
