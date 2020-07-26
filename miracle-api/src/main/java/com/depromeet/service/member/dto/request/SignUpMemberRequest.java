package com.depromeet.service.member.dto.request;

import com.depromeet.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignUpMemberRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    private String profileUrl;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignUpMemberRequest(String email, String name, String profileUrl) {
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public Member toEntity() {
        return Member.newInstance(email, name, profileUrl);
    }

}
