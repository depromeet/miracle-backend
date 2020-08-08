package com.depromeet.domain.alarm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmScheduleCreator {

    public static AlarmSchedule createAlarmSchedule(Long memberId, AlarmType type, String description) {
        return AlarmSchedule.builder()
            .memberId(memberId)
            .type(type)
            .description(description)
            .build();
    }

}
