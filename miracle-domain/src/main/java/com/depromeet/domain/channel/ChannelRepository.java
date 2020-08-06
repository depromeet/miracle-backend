package com.depromeet.domain.channel;

import com.depromeet.domain.channel.repository.ChannelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom {

}
