package com.depromeet.service.channel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RetrieveChannelInfoRequest {

    @NotBlank
    private String uuid;

    private RetrieveChannelInfoRequest(String uuid) {
        this.uuid = uuid;
    }

    public static RetrieveChannelInfoRequest testInstance(String uuid) {
        return new RetrieveChannelInfoRequest(uuid);
    }

}
