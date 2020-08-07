package com.depromeet.service.member.dto.request;

import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberInfoRequest {

    private String name;

    private ProfileIcon profileIcon;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateMemberInfoRequest(String name, ProfileIcon profileIcon) {
        this.name = name;
        this.profileIcon = profileIcon;
    }

}
