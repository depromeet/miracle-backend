package com.depromeet.service.alarm.dto.request;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UpdateAlarmScheduleRequest {

    @NotNull(message = "수정하려는 알람 스케쥴의 아이디를 입력해주세요.")
    private Long alarmScheduleId;

    @NotNull(message = "알람의 종류를 입력해주세요.")
    private AlarmType type;

    private String description;

    @NotNull(message = "알람 리스트를 입력해주세요.")
    private List<AlarmRequest> alarms;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpdateAlarmScheduleRequest(Long alarmScheduleId, AlarmType type, String description, List<AlarmRequest> alarms) {
        this.alarmScheduleId = alarmScheduleId;
        this.type = type;
        this.description = description;
        this.alarms = alarms;
    }

    public AlarmSchedule toEntity(Long memberId) {
        List<Alarm> alarmList = alarms.stream()
            .map(AlarmRequest::toEntity)
            .collect(Collectors.toList());
        AlarmSchedule alarmSchedule = AlarmSchedule.newInstance(memberId, type, description);
        alarmSchedule.addAlarms(alarmList);
        return alarmSchedule;
    }

    public List<Alarm> toAlarmsEntity() {
        return alarms.stream()
            .map(AlarmRequest::toEntity)
            .collect(Collectors.toList());
    }

}
