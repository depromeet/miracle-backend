package com.depromeet.controller.member;

import com.depromeet.ApiResponse;
import com.depromeet.service.member.MemberService;
import com.depromeet.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> signUpMember(@Valid @RequestBody SignUpMemberRequest request) {
        return ApiResponse.of(memberService.signUpMember(request));
    }

}
