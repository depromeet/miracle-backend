package com.depromeet.service.member.dto.response;

import com.depromeet.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {

    private Long id;

    private String email;

    private String name;

    private String profileUrl;

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail(), member.getName(), member.getProfileUrl());
    }

}
