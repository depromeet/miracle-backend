package com.depromeet.service.alarm.dto.request;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.DayOfTheWeek;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CreateAlarmRequest {

    private DayOfTheWeek dayOfTheWeek;

    private LocalTime reminderTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public CreateAlarmRequest(DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.reminderTime = reminderTime;
    }

    Alarm toEntity() {
        return Alarm.newInstance(dayOfTheWeek, reminderTime);
    }

}
