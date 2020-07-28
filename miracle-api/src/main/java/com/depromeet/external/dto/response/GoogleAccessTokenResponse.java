package com.depromeet.external.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class GoogleAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public GoogleAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
