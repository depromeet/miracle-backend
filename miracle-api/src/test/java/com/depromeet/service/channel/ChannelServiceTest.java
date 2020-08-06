package com.depromeet.service.channel;

import com.depromeet.domain.channel.Channel;
import com.depromeet.domain.channel.ChannelCreator;
import com.depromeet.domain.channel.ChannelMemberMapper;
import com.depromeet.domain.channel.ChannelMemberMapperRepository;
import com.depromeet.domain.channel.ChannelRepository;
import com.depromeet.domain.channel.ChannelRole;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.service.channel.dto.request.CreateChannelRequest;
import com.depromeet.service.channel.dto.request.EnterChannelRequest;
import com.depromeet.service.channel.dto.request.ExitChannelRequest;
import com.depromeet.service.channel.dto.request.RetrieveChannelRequest;
import com.depromeet.service.channel.dto.request.UpdateChannelRequest;
import com.depromeet.service.channel.dto.response.ChannelInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelMemberMapperRepository channelMemberMapperRepository;

    @Autowired
    private ChannelService channelService;

    private Long memberId;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAllInBatch();
        channelMemberMapperRepository.deleteAllInBatch();
        channelRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setUpMember() {
        memberId = memberRepository.save(MemberCreator.create("will.seungho@gmail.com")).getId();
    }

    @Test
    void 채널을_생성한다() {
        // given
        String name = "디프만";
        String introduction = "디프만 화이팅!";
        String profileUrl = "profileUrl";
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 4, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 5, 0, 0, 0);

        CreateChannelRequest request = CreateChannelRequest.testBuilder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .build();

        // when
        channelService.createChannel(request, memberId);

        // then
        List<Channel> channels = channelRepository.findAll();
        assertThat(channels).hasSize(1);
        assertChannelInfo(channels.get(0), name, introduction, profileUrl, startDateTime, endDateTime);
        assertThat(channels.get(0).getMembersCount()).isEqualTo(1);
    }

    @Test
    void 채널을_생성한_사람이_생성자가_된다() {
        // given
        String name = "디프만";

        CreateChannelRequest request = CreateChannelRequest.testBuilder()
            .name(name)
            .build();

        // when
        channelService.createChannel(request, memberId);

        // then
        List<ChannelMemberMapper> channelMemberMappers = channelMemberMapperRepository.findAll();
        assertThat(channelMemberMappers).hasSize(1);
        assertThat(channelMemberMappers.get(0).getRole()).isEqualTo(ChannelRole.CREATOR);
    }

    @Test
    void 채널의_정보를_수정한다() {
        // given
        String name = "newName";
        String introduction = "newIntroduction";
        String profileUrl = "newProfileUrl";
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 4, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 5, 0, 0, 0);

        Channel channel = ChannelCreator.create("name");
        channel.addCreator(memberId);
        channelRepository.save(channel);

        UpdateChannelRequest request = UpdateChannelRequest.testBuilder()
            .channelUuid(channel.getUUidStr())
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .build();

        // when
        channelService.updateChannelInfo(request, memberId);

        // then
        List<Channel> channels = channelRepository.findAll();
        assertThat(channels).hasSize(1);
        assertChannelInfo(channels.get(0), name, introduction, profileUrl, startDateTime, endDateTime);
    }

    @Test
    void 채널의_생성자만이_채널_정보를_수정할_수있다() {
        // given
        Channel channel = ChannelCreator.create("name");
        channel.addParticipant(memberId);
        channelRepository.save(channel);

        UpdateChannelRequest request = UpdateChannelRequest.testBuilder()
            .channelUuid(channel.getUUidStr())
            .name("something")
            .build();

        // when & then
        assertThatThrownBy(() -> {
            channelService.updateChannelInfo(request, memberId);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 자신이_참가하고_있는_채널_리스트를_불러온다() {
        // given
        String name = "name";
        String introduction = "introduction";
        String profileUrl = "profileUrl";
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 4, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 5, 0, 0, 0);

        Channel channel = ChannelCreator.create(name, introduction, profileUrl, startDateTime, endDateTime);
        channel.addCreator(memberId);
        channelRepository.save(channel);

        // when
        List<ChannelInfoResponse> response = channelService.retrieveMyChannelsInfo(memberId);

        // then
        assertThat(response).hasSize(1);
        assertChannelInfoResponse(response.get(0), name, introduction, profileUrl, startDateTime, endDateTime);
    }

    @Test
    void 특정_공개_채널의_정보를_불러온다() {
        // given
        String name = "name";
        String introduction = "introduction";
        String profileUrl = "profileUrl";
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 4, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 5, 0, 0, 0);

        Channel channel = ChannelCreator.create(name, introduction, profileUrl, startDateTime, endDateTime);
        channelRepository.save(channel);

        RetrieveChannelRequest request = RetrieveChannelRequest.testInstance(channel.getUUidStr());

        // when
        ChannelInfoResponse response = channelService.retrieveChannelInfo(request);

        // then
        assertChannelInfoResponse(response, name, introduction, profileUrl, startDateTime, endDateTime);
    }

    @Test
    void 채널에_입장하면_채널에_참가자로_포함된다() {
        // given
        Channel channel = ChannelCreator.create("name");
        channelRepository.save(channel);

        EnterChannelRequest request = EnterChannelRequest.testInstance(channel.getUUidStr());

        // when
        channelService.enterTheChannel(request, memberId);

        // then
        List<Channel> channels = channelRepository.findAll();
        assertThat(channels.get(0).getMembersCount()).isEqualTo(1);

        List<ChannelMemberMapper> channelMemberMappers = channelMemberMapperRepository.findAll();
        assertThat(channelMemberMappers).hasSize(1);
        assertThat(channelMemberMappers.get(0).getRole()).isEqualTo(ChannelRole.PARTICIPANT);
    }

    @Test
    void 이미_입장한_채널에_입장하려는_경우() {
        // given
        Channel channel = ChannelCreator.create("name");
        channel.addParticipant(memberId);
        channelRepository.save(channel);

        EnterChannelRequest request = EnterChannelRequest.testInstance(channel.getUUidStr());

        // when & then
        assertThatThrownBy(() -> {
            channelService.enterTheChannel(request, memberId);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_채널에서_탈퇴한다() {
        // given
        Channel channel = ChannelCreator.create("name");
        channel.addParticipant(memberId);
        channelRepository.save(channel);

        ExitChannelRequest request = ExitChannelRequest.testInstance(channel.getUUidStr());

        // when
        channelService.exitFromChannel(request, memberId);

        // then
        List<Channel> channels = channelRepository.findAll();
        assertThat(channels.get(0).getMembersCount()).isEqualTo(0);

        List<ChannelMemberMapper> channelMemberMappers = channelMemberMapperRepository.findAll();
        assertThat(channelMemberMappers).isEmpty();
    }

    @Test
    void 채널의_생성자는_탈퇴할_수_없다() {
        // given
        Channel channel = ChannelCreator.create("name");
        channel.addCreator(memberId);
        channelRepository.save(channel);

        ExitChannelRequest request = ExitChannelRequest.testInstance(channel.getUUidStr());

        // when & then
        assertThatThrownBy(() -> {
            channelService.exitFromChannel(request, memberId);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertChannelInfoResponse(ChannelInfoResponse response, String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getIntroduction()).isEqualTo(introduction);
        assertThat(response.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(response.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(response.getEndDateTime()).isEqualTo(endDateTime);
    }

    private void assertChannelInfo(Channel channel, String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assertThat(channel.getName()).isEqualTo(name);
        assertThat(channel.getIntroduction()).isEqualTo(introduction);
        assertThat(channel.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(channel.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(channel.getEndDateTime()).isEqualTo(endDateTime);
    }

}
