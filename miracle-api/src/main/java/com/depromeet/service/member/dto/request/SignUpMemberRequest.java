package com.depromeet.service.member.dto.request;

import com.depromeet.common.Category;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class SignUpMemberRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotNull
    private ProfileIcon profileIcon;

    @NotNull
    private List<Category> goals;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignUpMemberRequest(String email, String name, ProfileIcon profileIcon, List<Category> goals) {
        this.email = email;
        this.name = name;
        this.profileIcon = profileIcon;
        this.goals = goals;
    }

    public Member toEntity() {
        return Member.newInstance(email, name, profileIcon, goals);
    }

}
