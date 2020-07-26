package com.depromeet.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberInfoRequest {

    private String name;

    private String profileUrl;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateMemberInfoRequest(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
    }

}
