package com.depromeet.service.member.dto.response;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberGoal;
import com.depromeet.domain.member.ProfileIcon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

    private final Long id;

    private final String email;

    private final String name;

    private final ProfileIcon profileIcon;

    private final List<Category> goals;

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail(), member.getName(), member.getProfileIcon(), convertMemberGoalsToCategories(member));
    }

    private static List<Category> convertMemberGoalsToCategories(Member member) {
        return member.getMemberGoals().stream()
            .map(MemberGoal::getCategory)
            .collect(Collectors.toList());
    }

}
