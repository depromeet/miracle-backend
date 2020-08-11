package com.depromeet.service.alarm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RetrieveAlarmScheduleRequest {

    @NotNull(message = "조회하려는 알람 스케쥴의 아이디를 입력해주세요.")
    private Long alarmScheduleId;

    private RetrieveAlarmScheduleRequest(Long alarmScheduleId) {
        this.alarmScheduleId = alarmScheduleId;
    }

    public static RetrieveAlarmScheduleRequest of(Long alarmScheduleId) {
        return new RetrieveAlarmScheduleRequest(alarmScheduleId);
    }

}
