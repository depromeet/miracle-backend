package com.depromeet.controller.authentication;

import com.depromeet.ApiResponse;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.service.authentication.GoogleOAuthService;
import com.depromeet.service.authentication.dto.response.GoogleOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class GoogleOAuthController {

    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/api/v1/auth/google")
    public ApiResponse<GoogleOAuthResponse> googleAuthentication(@Valid @RequestBody GoogleOAuthRequest request) {
        return ApiResponse.of(googleOAuthService.getGoogleAuthentication(request));
    }

}
