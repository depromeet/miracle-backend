package com.depromeet.controller.authentication;

import com.depromeet.ApiResponse;
import com.depromeet.config.session.MemberSession;
import com.depromeet.constants.SessionConstants;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.service.authentication.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class GoogleOAuthController {

    private final HttpSession httpSession;

    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/api/v1/auth/google")
    public ApiResponse<String> googleAuthentication(@Valid @RequestBody GoogleOAuthRequest request) {
        Long memberId = googleOAuthService.getGoogleAuthentication(request);
        httpSession.setAttribute(SessionConstants.LOGIN_USER, MemberSession.of(memberId));
        return ApiResponse.of(httpSession.getId());
    }

}
