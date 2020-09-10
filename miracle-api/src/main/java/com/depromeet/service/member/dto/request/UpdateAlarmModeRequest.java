package com.depromeet.service.member.dto.request;

import com.depromeet.domain.member.AlarmMode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateAlarmModeRequest {

    @NotNull(message = "알람 모드를 선택해주세요.")
    private AlarmMode alarmMode;

    private UpdateAlarmModeRequest(AlarmMode alarmMode) {
        this.alarmMode = alarmMode;
    }

    public static UpdateAlarmModeRequest testInstance(AlarmMode alarmMode) {
        return new UpdateAlarmModeRequest(alarmMode);
    }

}
