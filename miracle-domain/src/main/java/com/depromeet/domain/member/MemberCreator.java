package com.depromeet.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create(String email, String name, String profileUrl) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .build();
    }

}
