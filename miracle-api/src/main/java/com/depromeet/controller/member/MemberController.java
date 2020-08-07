package com.depromeet.controller.member;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.constants.SessionConstants;
import com.depromeet.service.member.MemberService;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberGoalsRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final HttpSession httpSession;

    /**
     * 회원가입 API
     */
    @PostMapping("/api/v1/member")
    public ApiResponse<String> signUpMember(@Valid @RequestBody SignUpMemberRequest request) {
        Long memberId = memberService.signUpMember(request);
        httpSession.setAttribute(SessionConstants.LOGIN_USER, MemberSession.of(memberId));
        return ApiResponse.of(httpSession.getId());
    }

    /**
     * 회원정보를 변경하는 API
     */
    @PutMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> updateMemberInfo(@Valid @RequestBody UpdateMemberInfoRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(memberService.updateMemberInfo(request, memberSession.getMemberId()));
    }

    /**
     * 회원의 목표정보를 변경하는 API
     */
    @PutMapping("/api/v1/member/goal")
    public ApiResponse<MemberInfoResponse> updateMemberGoals(@Valid @RequestBody UpdateMemberGoalsRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(memberService.updateMemberGoals(request, memberSession.getMemberId()));
    }

    /**
     * 내정보를 불러오는 API
     */
    @GetMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> getMemberInfo(@LoginMember MemberSession memberSession) {
        return ApiResponse.of(memberService.getMemberInfo(memberSession.getMemberId()));
    }

}
