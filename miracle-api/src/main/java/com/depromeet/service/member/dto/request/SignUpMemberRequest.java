package com.depromeet.service.member.dto.request;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
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
    @Size(max = 3)
    private List<Category> goals;

    @NotNull
    private LocalTime wakeUpTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignUpMemberRequest(String email, String name, ProfileIcon profileIcon, List<Category> goals, LocalTime wakeUpTime) {
        this.email = email;
        this.name = name;
        this.profileIcon = profileIcon;
        this.goals = goals;
        this.wakeUpTime = wakeUpTime;
    }

    public Member toEntity() {
        return Member.newInstance(email, name, profileIcon, goals);
    }

}
