package com.depromeet.controller.member;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.member.MemberService;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.request.UpdateMemberInfoRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     */
    @PostMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> signUpMember(@Valid @RequestBody SignUpMemberRequest request) {
        return ApiResponse.of(memberService.signUpMember(request)); // TODO MemberInfoResponse가 아닌 세션 ID or 토큰 반환해야함.
    }
    // TODO Google OAuth 프론트로 콜백해서 추가정보를 받고 회원가입 하지 않으면 삭제할 예정.

    /**
     * 회원정보를 변경하는 API
     */
    @PutMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> updateMemberInfo(@Valid @RequestBody UpdateMemberInfoRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(memberService.updateMemberInfo(request, memberSession.getMemberId()));
    }

    /**
     * 내정보를 불러오는 API
     */
    @GetMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> getMemberInfo(@LoginMember MemberSession memberSession) {
        return ApiResponse.of(memberService.getMemberInfo(memberSession.getMemberId()));
    }

}
