package com.depromeet.domain.channel;

import com.depromeet.domain.common.Uuid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Uuid uuid;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelMemberMapper> channelMemberMappers = new ArrayList<>();

    @Embedded
    private ChannelGoal channelGoal;

    @Embedded
    private ChannelGoalPeriod channelGoalPeriod;

    private int membersCount;

    private String profileUrl;

    private String invitationKey; // TODO 초대키를 생성하는 로직 추가 + VO로 변경

    @Builder
    public Channel(String name, String introduction, String profileUrl, String invitationKey, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.uuid = Uuid.newInstance();
        this.membersCount = 0;
        this.channelGoal = ChannelGoal.of(name, introduction);
        this.channelGoalPeriod = ChannelGoalPeriod.of(startDateTime, endDateTime);
        this.profileUrl = profileUrl;
        this.invitationKey = invitationKey;
    }

    public static Channel newInstance(String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Channel.builder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .build();
    }

    public String getName() {
        if (channelGoal == null) {
            return null;
        }
        return channelGoal.getName();
    }

    public String getIntroduction() {
        if (channelGoal == null) {
            return null;
        }
        return channelGoal.getIntroduction();
    }

    public String getUUidStr() {
        return uuid.getUuid();
    }

    public LocalDateTime getStartDateTime() {
        if (channelGoalPeriod == null) {
            return null;
        }
        return channelGoalPeriod.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        if (channelGoalPeriod == null) {
            return null;
        }
        return channelGoalPeriod.getEndDateTime();
    }

    public void updateInfo(String name, String introduction, String profileUrl, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.channelGoal = ChannelGoal.of(name, introduction);
        this.channelGoalPeriod = ChannelGoalPeriod.of(startDateTime, endDateTime);
        this.profileUrl = profileUrl;
    }

    public void addParticipant(Long memberId) {
        validateAlreadyInChannel(memberId);
        ChannelMemberMapper channelMemberMapper = ChannelMemberMapper.of(this, memberId, ChannelRole.PARTICIPANT);
        this.channelMemberMappers.add(channelMemberMapper);
        this.membersCount++;
    }

    public void addCreator(Long memberId) {
        validateAlreadyInChannel(memberId);
        ChannelMemberMapper channelMemberMapper = ChannelMemberMapper.of(this, memberId, ChannelRole.CREATOR);
        this.channelMemberMappers.add(channelMemberMapper);
        this.membersCount++;
    }

    public void deleteParticipant(Long memberId) {
        if (isCreator(memberId)) {
            throw new IllegalArgumentException(String.format("채널 (%s) 의 생성자 (%s) 는 탈퇴할 수 없습니다", uuid, memberId));
        }
        if (!isParticipant(memberId)) {
            throw new IllegalArgumentException(String.format("멤버 (%s)는 채널 (%s)에 참가하고 있지 않습니다", memberId, getUUidStr()));
        }
        this.channelMemberMappers.removeIf(channelMemberMapper -> channelMemberMapper.isParticipant(memberId));
        this.membersCount--;
    }

    private void validateAlreadyInChannel(Long memberId) {
        if (isChannelMember(memberId)) {
            throw new IllegalArgumentException(String.format("멤버 (%s)는 이미 채널 (%s) 에 참가하고 있습니다", memberId, getUUidStr()));
        }
    }

    private boolean isChannelMember(Long memberId) {
        return isParticipant(memberId) || isCreator(memberId);
    }

    private boolean isParticipant(Long memberId) {
        return this.channelMemberMappers.stream()
            .anyMatch(channelMemberMapper -> channelMemberMapper.isParticipant(memberId));
    }

    public boolean isCreator(Long memberId) {
        return this.channelMemberMappers.stream()
            .anyMatch(channelMemberMapper -> channelMemberMapper.isCreator(memberId));
    }

}
