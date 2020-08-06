package com.depromeet.controller.channel;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.channel.ChannelService;
import com.depromeet.service.channel.dto.request.CreateChannelRequest;
import com.depromeet.service.channel.dto.request.EnterChannelRequest;
import com.depromeet.service.channel.dto.request.ExitChannelRequest;
import com.depromeet.service.channel.dto.request.UpdateChannelRequest;
import com.depromeet.service.channel.dto.response.ChannelInfoResponse;
import com.depromeet.service.channel.dto.request.RetrieveChannelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChannelController {

    private final ChannelService channelService;

    /**
     * 새로운 채널을 생성하는 API
     */
    @PostMapping("/api/v1/channel")
    public ApiResponse<ChannelInfoResponse> createChannel(@Valid @RequestBody CreateChannelRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(channelService.createChannel(request, memberSession.getMemberId()));
    }

    /**
     * 채널의 정보를 변경하는 API
     */
    @PutMapping("/api/v1/channel")
    public ApiResponse<ChannelInfoResponse> updateChannelInfo(@Valid @RequestBody UpdateChannelRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(channelService.updateChannelInfo(request, memberSession.getMemberId()));
    }

    /**
     * 내가 속한 채널들의 정보를 검색하는 API
     */
    @GetMapping("/api/v1/channel/my")
    public ApiResponse<List<ChannelInfoResponse>> retrieveMyChannelsInfo(@LoginMember MemberSession memberSession) {
        return ApiResponse.of(channelService.retrieveMyChannelsInfo(memberSession.getMemberId()));
    }

    /**
     * 특정 채널의 정보를 검색하는 API
     */
    @GetMapping("/api/v1/channel")
    public ApiResponse<ChannelInfoResponse> retrieveChannelInfo(@Valid RetrieveChannelRequest request) {
        return ApiResponse.of(channelService.retrieveChannelInfo(request));
    }

    /**
     * 특정 채널에 입장하는 API
     */
    @PutMapping("/api/v1/channel/enter")
    public ApiResponse<ChannelInfoResponse> enterTheChannel(@Valid @RequestBody EnterChannelRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(channelService.enterTheChannel(request, memberSession.getMemberId()));
    }

    /**
     * 특정 채널에서 탈퇴하는 API
     */
    @PutMapping("/api/v1/channel/exit")
    public ApiResponse<ChannelInfoResponse> exitFromChannel(@Valid @RequestBody ExitChannelRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(channelService.exitFromChannel(request, memberSession.getMemberId()));
    }

}
