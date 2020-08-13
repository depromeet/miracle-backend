package com.depromeet.service.member.dto.request;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.member.MemberGoal;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UpdateMemberGoalsRequest {

    @NotNull(message = "목표를 설정해주세요.")
    @Size(max = 3, message = "목표를 3개 이하 선택해주세요.")
    private List<Category> goals;

    private UpdateMemberGoalsRequest(List<Category> goals) {
        this.goals = goals;
    }

    public static UpdateMemberGoalsRequest testInstance(List<Category> goals) {
        return new UpdateMemberGoalsRequest(goals);
    }

    public List<MemberGoal> toMemberGoals() {
        return this.goals.stream()
            .map(MemberGoal::of)
            .collect(Collectors.toList());
    }

}
