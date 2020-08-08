package com.depromeet.domain.channel.repository;

import com.depromeet.domain.channel.Channel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.domain.channel.QChannel.channel;
import static com.depromeet.domain.channel.QChannelMemberMapper.channelMemberMapper;

@RequiredArgsConstructor
public class ChannelRepositoryCustomImpl implements ChannelRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Channel findChannelByUuid(String uuid) {
        return queryFactory.selectFrom(channel).distinct()
            .leftJoin(channel.channelMemberMappers, channelMemberMapper).fetchJoin()
            .where(
                channel.uuid.uuid.eq(uuid)
            ).fetchOne();
    }

    @Override
    public List<Channel> findChannelsByMemberId(Long memberId) {
        return queryFactory.selectFrom(channel).distinct()
            .leftJoin(channel.channelMemberMappers, channelMemberMapper).fetchJoin()
            .where(
                channelMemberMapper.memberId.eq(memberId)
            ).fetch();
    }

}
