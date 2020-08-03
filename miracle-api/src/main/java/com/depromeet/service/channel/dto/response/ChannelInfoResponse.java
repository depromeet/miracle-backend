package com.depromeet.service.channel.dto.response;

import com.depromeet.domain.channel.Channel;
import com.depromeet.domain.channel.ChannelAccess;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ChannelInfoResponse {

    private final String uuid;

    private final String name;

    private final String introduction;

    private final int membersCount;

    private final String profileUrl;

    private final ChannelAccess access;

    public static ChannelInfoResponse of(Channel channel) {
        return new ChannelInfoResponse(channel.getUUidStr(), channel.getName(), channel.getIntroduction(),
            channel.getMembersCount(), channel.getProfileUrl(), channel.getAccess());
    }

}
