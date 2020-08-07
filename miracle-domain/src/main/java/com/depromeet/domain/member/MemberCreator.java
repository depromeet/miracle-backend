package com.depromeet.domain.member;

import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create(String email) {
        return Member.builder()
            .email(email)
            .name("이름")
            .build();
    }

    public static Member create(String email, String name) {
        return Member.builder()
            .email(email)
            .name(name)
            .build();
    }

    public static Member create(String email, String name, ProfileIcon profileIcon, List<Category> goals) {
        return Member.builder()
            .email(email)
            .name(name)
            .profileIcon(profileIcon)
            .goals(goals)
            .build();
    }

}
