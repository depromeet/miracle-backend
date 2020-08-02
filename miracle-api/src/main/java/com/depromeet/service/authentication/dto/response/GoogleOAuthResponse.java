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

    private final String profileUrl;

    public static GoogleOAuthResponse signUp(String email, String name, String profileUrl) {
        return new GoogleOAuthResponse(OAuthType.SIGN_UP, null, email, name, profileUrl);
    }

    public static GoogleOAuthResponse login(String sessionId) {
        return new GoogleOAuthResponse(OAuthType.LOGIN, sessionId, null, null, null);
    }

    public enum OAuthType {
        LOGIN, SIGN_UP
    }

}
