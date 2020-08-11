package com.depromeet.service.alarm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class DeleteAlarmScheduleRequest {

    @NotNull(message = "삭제하려는 알람 스케쥴의 아이디를 입력해주세요.")
    private Long alarmScheduleId;

    private DeleteAlarmScheduleRequest(Long alarmScheduleId) {
        this.alarmScheduleId = alarmScheduleId;
    }

    public static DeleteAlarmScheduleRequest testInstance(Long alarmScheduleId) {
        return new DeleteAlarmScheduleRequest(alarmScheduleId);
    }

}
