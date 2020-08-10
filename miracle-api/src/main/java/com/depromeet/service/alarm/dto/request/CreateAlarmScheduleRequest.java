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
public class CreateAlarmScheduleRequest {

    private String description;

    @NotNull
    private AlarmType type;

    @NotNull
    private List<AlarmRequest> alarms;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public CreateAlarmScheduleRequest(String description, AlarmType type, List<AlarmRequest> alarms) {
        this.description = description;
        this.type = type;
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

}
