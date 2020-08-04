package com.depromeet.external.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class GoogleUserProfileResponse {

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("name")
    private String name;

    @JsonProperty("locale")
    private String locale;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public GoogleUserProfileResponse(String email, String picture, String name, String locale) {
        this.email = email;
        this.picture = picture;
        this.name = name;
        this.locale = locale;
    }

}
