package com.depromeet.service.alarm.dto.request;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CreateAlarmScheduleRequest {

    private String description;

    private AlarmType type;

    private List<CreateAlarmRequest> alarms;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public CreateAlarmScheduleRequest(String description, AlarmType type, List<CreateAlarmRequest> alarms) {
        this.description = description;
        this.type = type;
        this.alarms = alarms;
    }

    public AlarmSchedule toEntity(Long memberId) {
        List<Alarm> alarmList = alarms.stream()
            .map(CreateAlarmRequest::toEntity)
            .collect(Collectors.toList());
        AlarmSchedule alarmSchedule = AlarmSchedule.newInstance(memberId, type, description);
        alarmSchedule.addAlarms(alarmList);
        return alarmSchedule;
    }

}
