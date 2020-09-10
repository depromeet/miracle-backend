package com.depromeet.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create(String email) {
        return Member.builder()
            .email(email)
            .name("이름")
            .profileIcon(ProfileIcon.BLUE)
            .alarmMode(AlarmMode.BASIC)
            .build();
    }

    public static Member create(String email, String name, ProfileIcon profileIcon) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .alarmMode(AlarmMode.BASIC)
            .build();
    }

}
