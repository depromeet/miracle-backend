package com.depromeet.service.authentication.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class GoogleOAuthRequest {

    @NotBlank
    private String code;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public GoogleOAuthRequest(String code) {
        this.code = code;
    }

}
