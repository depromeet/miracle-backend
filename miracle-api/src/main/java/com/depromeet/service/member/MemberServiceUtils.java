package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberServiceUtils {

    static void validateNonExistMember(MemberRepository memberRepository, String email) {
        if (memberRepository.findMemberByEmail(email) != null) {
            throw new IllegalArgumentException(String.format("이미 존재하는 멤버 (%s) 입니다", email));
        }
    }

    static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        if (member == null) {
            throw new IllegalArgumentException(String.format("멤버 (%s)는 존재하지 않습니다", memberId));
        }
        return member;
    }

}
