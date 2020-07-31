package com.depromeet.service.authentication.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleOAuthRequest {

    private String code;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public GoogleOAuthRequest(String code) {
        this.code = code;
    }

}
