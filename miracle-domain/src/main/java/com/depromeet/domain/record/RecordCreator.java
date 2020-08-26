package com.depromeet.domain.record;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordCreator {

    public static Record create(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Record.builder()
            .memberId(memberId)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .build();
    }

}
