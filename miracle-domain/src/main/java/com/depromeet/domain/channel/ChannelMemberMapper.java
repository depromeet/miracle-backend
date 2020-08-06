package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChannelMemberMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelRole role;

    private ChannelMemberMapper(Channel channel, Long memberId, ChannelRole role) {
        this.channel = channel;
        this.memberId = memberId;
        this.role = role;
    }

    public static ChannelMemberMapper of(Channel channel, Long memberId, ChannelRole role) {
        return new ChannelMemberMapper(channel, memberId, role);
    }

    boolean isParticipant(Long memberId) {
        return this.memberId.equals(memberId) && this.role.equals(ChannelRole.PARTICIPANT);
    }

    boolean isCreator(Long memberId) {
        return this.memberId.equals(memberId) && this.role.equals(ChannelRole.CREATOR);
    }

}
