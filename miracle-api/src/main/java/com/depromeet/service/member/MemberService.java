package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfoResponse signUpMember(SignUpMemberRequest request) {
        MemberServiceUtils.validateNonExistMember(memberRepository, request.getEmail());
        Member newMember = memberRepository.save(request.toEntity());
        return MemberInfoResponse.of(newMember);
    }

}
