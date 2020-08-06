package com.depromeet.service.channel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RetrieveChannelRequest {

    @NotBlank
    private String channelUuid;

    private RetrieveChannelRequest(String channelUuid) {
        this.channelUuid = channelUuid;
    }

    public static RetrieveChannelRequest testInstance(String channelUuid) {
        return new RetrieveChannelRequest(channelUuid);
    }

}
