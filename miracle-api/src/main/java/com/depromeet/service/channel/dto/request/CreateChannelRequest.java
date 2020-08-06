package com.depromeet.service.channel.dto.request;

import com.depromeet.domain.channel.Channel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateChannelRequest {

    @NotBlank
    private String name;

    private String introduction;

    private String profileUrl;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public CreateChannelRequest(String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Channel toEntity() {
        return Channel.newInstance(name, introduction, profileUrl, startDateTime, endDateTime);
    }

}
