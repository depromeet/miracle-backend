package com.depromeet.service.alarm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class DeleteAlarmScheduleRequest {

    @NotNull
    private Long alarmScheduleId;

    private DeleteAlarmScheduleRequest(Long alarmScheduleId) {
        this.alarmScheduleId = alarmScheduleId;
    }

    public static DeleteAlarmScheduleRequest testInstance(Long alarmScheduleId) {
        return new DeleteAlarmScheduleRequest(alarmScheduleId);
    }

}
