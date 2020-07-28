package com.depromeet.service.authentication.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleOAuthRequest {

    private String code;

    private String redirectUri;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public GoogleOAuthRequest(String code, String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
    }

}
