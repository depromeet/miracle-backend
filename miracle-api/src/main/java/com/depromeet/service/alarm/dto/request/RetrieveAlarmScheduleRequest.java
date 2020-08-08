package com.depromeet.service.alarm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RetrieveAlarmScheduleRequest {

    private Long alarmScheduleId;

    private RetrieveAlarmScheduleRequest(Long alarmScheduleId) {
        this.alarmScheduleId = alarmScheduleId;
    }

    public static RetrieveAlarmScheduleRequest of(Long alarmScheduleId) {
        return new RetrieveAlarmScheduleRequest(alarmScheduleId);
    }

}
