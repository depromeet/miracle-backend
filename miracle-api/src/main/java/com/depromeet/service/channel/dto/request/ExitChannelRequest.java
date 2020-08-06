package com.depromeet.service.channel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ExitChannelRequest {

    @NotBlank
    private String channelUuid;

    private ExitChannelRequest(String channelUuid) {
        this.channelUuid = channelUuid;
    }

    public static ExitChannelRequest testInstance(String channelUuid) {
        return new ExitChannelRequest(channelUuid);
    }

}
