package com.depromeet.service.channel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateChannelRequest {

    @NotBlank
    private String channelUuid;

    @NotBlank
    private String name;

    private String introduction;

    private String profileUrl;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateChannelRequest(String channelUuid, String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.channelUuid = channelUuid;
        this.name = name;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

}
