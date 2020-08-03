package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelCreator {

    public static Channel createPublic(String name) {
        return Channel.builder()
            .name(name)
            .access(ChannelAccess.PUBLIC)
            .build();
    }

    public static Channel createPublic(String name, String introduction, String profileUrl) {
        return Channel.builder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .access(ChannelAccess.PUBLIC)
            .build();
    }

    public static Channel createPrivate(String name) {
        return Channel.builder()
            .name(name)
            .access(ChannelAccess.PRIVATE)
            .build();
    }

    public static Channel createPrivate(String name, String introduction, String profileUrl) {
        return Channel.builder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .access(ChannelAccess.PRIVATE)
            .build();
    }

}
