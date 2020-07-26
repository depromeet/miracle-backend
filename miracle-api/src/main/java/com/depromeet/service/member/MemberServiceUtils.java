package com.depromeet.service.member;

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

}
