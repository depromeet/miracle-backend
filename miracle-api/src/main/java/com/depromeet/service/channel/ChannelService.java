package com.depromeet.service.channel;

import com.depromeet.domain.channel.Channel;
import com.depromeet.domain.channel.ChannelRepository;
import com.depromeet.service.channel.dto.request.EnterChannelRequest;
import com.depromeet.service.channel.dto.request.CreateChannelRequest;
import com.depromeet.service.channel.dto.request.ExitChannelRequest;
import com.depromeet.service.channel.dto.request.UpdateChannelRequest;
import com.depromeet.service.channel.dto.response.ChannelInfoResponse;
import com.depromeet.service.channel.dto.request.RetrieveChannelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    public ChannelInfoResponse createChannel(CreateChannelRequest request, Long memberId) {
        Channel channel = request.toEntity();
        channel.addCreator(memberId);
        return ChannelInfoResponse.of(channelRepository.save(channel));
    }

    @Transactional
    public ChannelInfoResponse updateChannelInfo(UpdateChannelRequest request, Long memberId) {
        Channel channel = ChannelServiceUtils.findChannelByUuid(channelRepository, request.getChannelUuid());
        ChannelServiceUtils.validateChannelCreator(channel, memberId);
        channel.updateInfo(request.getName(), request.getIntroduction(), request.getProfileUrl(), request.getStartDateTime(), request.getEndDateTime());
        return ChannelInfoResponse.of(channel);
    }

    @Transactional(readOnly = true)
    public List<ChannelInfoResponse> retrieveChannelsInfo(Long memberId) {
        return channelRepository.findChannelsByMemberId(memberId).stream()
            .map(ChannelInfoResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChannelInfoResponse retrieveChannelInfo(RetrieveChannelRequest request) {
        Channel channel = ChannelServiceUtils.findChannelByUuid(channelRepository, request.getChannelUuid());
        return ChannelInfoResponse.of(channel);
    }

    @Transactional
    public ChannelInfoResponse enterTheChannel(EnterChannelRequest request, Long memberId) {
        Channel channel = ChannelServiceUtils.findChannelByUuid(channelRepository, request.getChannelUuid());
        channel.addParticipant(memberId);
        return ChannelInfoResponse.of((channel));
    }

    @Transactional
    public ChannelInfoResponse exitFromChannel(ExitChannelRequest request, Long memberId) {
        Channel channel = ChannelServiceUtils.findChannelByUuid(channelRepository, request.getChannelUuid());
        channel.deleteParticipant(memberId);
        return ChannelInfoResponse.of(channel);
    }

}
