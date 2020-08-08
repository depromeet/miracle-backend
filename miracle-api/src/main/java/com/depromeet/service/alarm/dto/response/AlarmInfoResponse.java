package com.depromeet.service.alarm.dto.response;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.DayOfTheWeek;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmInfoResponse {

    private final DayOfTheWeek dayOfTheWeek;

    private final LocalTime reminderTime;

    public static AlarmInfoResponse of(Alarm alarm) {
        return new AlarmInfoResponse(alarm.getDayOfTheWeek(), alarm.getReminderTime());
    }

}
