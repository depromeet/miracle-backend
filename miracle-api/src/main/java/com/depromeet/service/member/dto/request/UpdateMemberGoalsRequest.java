package com.depromeet.service.member.dto.request;

import com.depromeet.domain.common.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateMemberGoalsRequest {

    @NotNull
    private List<Category> goals;

    private UpdateMemberGoalsRequest(List<Category> goals) {
        this.goals = goals;
    }

    public static UpdateMemberGoalsRequest testInstance(List<Category> goals) {
        return new UpdateMemberGoalsRequest(goals);
    }

}
