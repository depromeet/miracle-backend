package com.depromeet.service.authentication.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleOAuthResponse {

    private final OAuthType type;

    private final String sessionId;

    private final String email;

    private final String name;

    public static GoogleOAuthResponse signUp(String email, String name) {
        return new GoogleOAuthResponse(OAuthType.SIGN_UP, null, email, name);
    }

    public static GoogleOAuthResponse login(String sessionId) {
        return new GoogleOAuthResponse(OAuthType.LOGIN, sessionId, null, null);
    }

    public enum OAuthType {
        LOGIN, SIGN_UP
    }

}
