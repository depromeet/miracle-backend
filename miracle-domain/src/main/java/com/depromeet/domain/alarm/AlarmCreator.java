package com.depromeet.domain.alarm;

import com.depromeet.domain.common.DayOfTheWeek;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmCreator {

    public static Alarm createAlarm(DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        return Alarm.builder()
            .dayOfTheWeek(dayOfTheWeek)
            .reminderTime(reminderTime)
            .build();
    }

}
