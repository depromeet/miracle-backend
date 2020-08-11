package com.depromeet.service.alarm.dto.request;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.common.DayOfTheWeek;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class AlarmRequest {

    @NotNull(message = "알람을 받을 요일을 선택해주세요.")
    private DayOfTheWeek dayOfTheWeek;

    @NotNull(message = "알람을 받을 시간을 입력해주세요.")
    private LocalTime reminderTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AlarmRequest(DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.reminderTime = reminderTime;
    }

    Alarm toEntity() {
        return Alarm.newInstance(dayOfTheWeek, reminderTime);
    }

}
