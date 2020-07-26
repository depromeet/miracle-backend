package com.depromeet.service.member.dto.response;

import com.depromeet.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberInfoResponse {

    private final Long id;

    private final String email;

    private final String name;

    private final String profileUrl;

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail(), member.getName(), member.getProfileUrl());
    }

}
