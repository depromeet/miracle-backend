package com.depromeet.domain.channel;

import com.depromeet.domain.common.Uuid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @Column(nullable = false)
    private String name;

    private String introduction;

    private int membersCount;

    private String invitationKey; // TODO 초대키를 생성하는 로직 추가 + VO로 변경

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private ChannelAccess access;

    @Builder
    public Channel(String name, String introduction, String profileUrl, String invitationKey, ChannelAccess access) {
        this.uuid = Uuid.newInstance();
        this.name = name;
        this.introduction = introduction;
        this.membersCount = 0;
        this.profileUrl = profileUrl;
        this.invitationKey = invitationKey;
        this.access = access;
    }

    public static Channel newInstance(String name, String introduction, String profileUrl, ChannelAccess access) {
        return Channel.builder()
            .name(name)
            .introduction(introduction)
            .profileUrl(profileUrl)
            .access(access)
            .build();
    }

    public String getUUidStr() {
        return uuid.getUuid();
    }

    public void updateInfo(String name, String introduction, String profileUrl, ChannelAccess access) {
        this.name = name;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.access = access;
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

    public boolean isPublicAccess() {
        return access.equals(ChannelAccess.PUBLIC);
    }

}
