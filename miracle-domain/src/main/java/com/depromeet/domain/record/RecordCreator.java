package com.depromeet.domain.record;

import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordCreator {

    public static Record create(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Record.builder()
            .memberId(memberId)
            .scheduleId(1L)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .category(Category.EXERCISE)
            .question("question")
            .answer("answer")
            .build();
    }

}
