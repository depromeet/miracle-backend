package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.deprommet.exception.ConflictException;
import com.deprommet.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberServiceUtils {

    static void validateNonExistMember(MemberRepository memberRepository, String email) {
        if (memberRepository.findMemberByEmail(email) != null) {
            log.info(String.format("이미 가입한 멤버 (%s) 입니다", email));
            throw new ConflictException("이미 가입한 멤버입니다");
        }
    }

    static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        if (member == null) {
            log.info(String.format("해당 멤버 (%s)는 존재하지 않습니다", memberId));
            throw new NotFoundException("해당 멤버를 찾을 수 없습니다");
        }
        return member;
    }

}
