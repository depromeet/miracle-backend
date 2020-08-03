package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

    private String uuid; // TODO convert to Value Object (Uuid)

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelMemberMapper> channelMemberMappers = new ArrayList<>();

    private String name; // 중복 가능 or 불가능 할지?

    private String introduction;

    private int membersCount;

    private String invitationKey; // TODO convert to Value Object (InvitationKey)

    private String profileUrl;

}
