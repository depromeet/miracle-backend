package com.depromeet.domain.member;

import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberGoalCreator {

    public static MemberGoal create(Category category) {
        return MemberGoal.of(category);
    }
    
}
