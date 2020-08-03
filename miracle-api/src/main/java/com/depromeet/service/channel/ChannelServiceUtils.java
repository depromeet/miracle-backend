package com.depromeet.service.channel;

import com.depromeet.domain.channel.Channel;
import com.depromeet.domain.channel.ChannelRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ChannelServiceUtils {

    static Channel findChannelByUuid(ChannelRepository channelRepository, String uuid) {
        Channel channel = channelRepository.findChannelByUuid(uuid);
        if (channel == null) {
            throw new IllegalArgumentException(String.format("해당하는 uuid (%s) 를 가진 채널은 존재하지 않습니다", uuid));
        }
        return channel;
    }

    static void validateChannelCreator(Channel channel, Long memberId) {
        if (!channel.isCreator(memberId)) {
            throw new IllegalArgumentException(String.format("멤버 (%s) 는 채널 (%s) 의 생성자가 아닙니디", memberId, channel.getUUidStr()));
        }
    }

    static void validatePublicChannel(Channel channel) {
        if (!channel.isPublicAccess()) {
            throw new IllegalArgumentException(String.format("채널 (%s) 는 공개 채널이 아닙니다", channel.getUUidStr()));
        }
    }

}
