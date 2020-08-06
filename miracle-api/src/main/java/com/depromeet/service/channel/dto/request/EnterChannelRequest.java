package com.depromeet.service.channel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EnterChannelRequest {

    @NotBlank
    private String channelUuid;

    private EnterChannelRequest(String channelUuid) {
        this.channelUuid = channelUuid;
    }

    public static EnterChannelRequest testInstance(String channelUuid) {
        return new EnterChannelRequest(channelUuid);
    }

}
