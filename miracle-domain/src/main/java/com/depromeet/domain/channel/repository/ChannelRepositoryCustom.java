package com.depromeet.domain.channel.repository;

import com.depromeet.domain.channel.Channel;

import java.util.List;

public interface ChannelRepositoryCustom {

    Channel findChannelByUuid(String uuid);

    List<Channel> findChannelsByMemberId(Long memberId);

}
