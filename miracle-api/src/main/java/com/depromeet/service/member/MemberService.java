package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public MemberInfoResponse updateMemberInfo(UpdateMemberInfoRequest request, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        member.updateInfo(request.getName(), request.getProfileUrl());
        return MemberInfoResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        return MemberInfoResponse.of(member);
    }

}
