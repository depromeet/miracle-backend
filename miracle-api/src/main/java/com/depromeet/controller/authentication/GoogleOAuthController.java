package com.depromeet.controller.authentication;

import com.depromeet.ApiResponse;
import com.depromeet.constants.SessionConstants;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.service.authentication.GoogleOAuthService;
import com.depromeet.service.authentication.dto.response.GoogleOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class GoogleOAuthController {

    private final GoogleOAuthService googleOAuthService;
    private final HttpSession httpSession;

    /**
     * 구글 OAuth 를 요청하는 API
     */
    @PostMapping("/api/v1/auth/google")
    public ApiResponse<GoogleOAuthResponse> googleAuthentication(@Valid @RequestBody GoogleOAuthRequest request) {
        return ApiResponse.of(googleOAuthService.getGoogleAuthentication(request));
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/api/v1/logout")
    public ApiResponse<String> handleLogout() {
        httpSession.removeAttribute(SessionConstants.LOGIN_USER);
        return ApiResponse.SUCCESS;
    }

}
