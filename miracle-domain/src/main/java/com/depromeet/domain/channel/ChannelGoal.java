package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ChannelGoal {

    private String name;

    private String introduction;

    private ChannelGoal(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }

    public static ChannelGoal of(String name, String introduction) {
        return new ChannelGoal(name, introduction);
    }

}
