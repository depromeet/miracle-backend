package com.depromeet.service.member;

import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.event.alarm.NewMemberRegisteredEvent;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberGoalsRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final ApplicationEventPublisher eventPublisher;
    private final MemberRepository memberRepository;

    @Transactional
    public Long signUpMember(SignUpMemberRequest request) {
        MemberServiceUtils.validateNonExistMember(memberRepository, request.getEmail());
        Member newMember = memberRepository.save(request.toEntity());
        eventPublisher.publishEvent(NewMemberRegisteredEvent.of(newMember.getId(), request.getWakeUpTime()));
        return newMember.getId();
    }

    @Transactional
    public MemberInfoResponse updateMemberInfo(UpdateMemberInfoRequest request, Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        member.updateInfo(request.getName(), request.getProfileIcon());
        return MemberInfoResponse.of(member);
    }

    @Transactional
    public MemberInfoResponse updateMemberGoals(UpdateMemberGoalsRequest request, Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        member.updateMemberGoals(request.getGoals());
        return MemberInfoResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        return MemberInfoResponse.of(member);
    }

}
