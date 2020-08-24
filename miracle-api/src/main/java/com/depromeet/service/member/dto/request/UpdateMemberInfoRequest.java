package com.depromeet.service.member.dto.request;

import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateMemberInfoRequest {

    private String name;

    private ProfileIcon profileIcon;

    private LocalTime wakeUpTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateMemberInfoRequest(String name, ProfileIcon profileIcon, LocalTime wakeUpTime) {
        this.name = name;
        this.profileIcon = profileIcon;
        this.wakeUpTime = wakeUpTime;
    }

}
