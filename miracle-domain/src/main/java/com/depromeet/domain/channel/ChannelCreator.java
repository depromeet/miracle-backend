package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelCreator {

    public static Channel create(String name) {
        return Channel.builder()
            .name(name)
            .build();
    }

    public static Channel create(String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Channel.builder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .build();
    }

}
