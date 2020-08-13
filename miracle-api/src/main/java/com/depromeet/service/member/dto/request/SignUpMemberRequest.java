package com.depromeet.service.member.dto.request;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberGoal;
import com.depromeet.domain.member.ProfileIcon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SignUpMemberRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String name;

    @NotNull(message = "프로필 아이콘을 선택해주세요.")
    private ProfileIcon profileIcon;

    @NotNull(message = "목표를 설정해주세요.")
    @Size(max = 3, message = "목표를 3개 이하 선택해주세요.")
    private List<Category> goals;

    @NotNull(message = "기상시간을 입력해주세요.")
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
        Member member = Member.newInstance(email, name, profileIcon);
        member.addMemberGoals(toMemberGoals());
        return member;
    }

    private List<MemberGoal> toMemberGoals() {
        return this.goals.stream()
            .map(MemberGoal::of)
            .collect(Collectors.toList());
    }

}
