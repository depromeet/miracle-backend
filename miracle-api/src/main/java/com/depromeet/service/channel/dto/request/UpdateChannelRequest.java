package com.depromeet.service.channel.dto.request;

import com.depromeet.domain.channel.ChannelAccess;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateChannelRequest {

    @NotBlank
    private String channelUuid;

    @NotBlank
    private String name;

    private String introduction;

    private String profileUrl;

    @NotNull
    private ChannelAccess access;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateChannelRequest(String channelUuid, String name, String introduction, String profileUrl, ChannelAccess access) {
        this.channelUuid = channelUuid;
        this.name = name;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.access = access;
    }

}
