package com.depromeet.service.alarm.dto.response;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmScheduleInfoResponse {

    private final Long id;

    private final AlarmType type;

    private final String description;

    private final List<AlarmInfoResponse> alarms;

    public static AlarmScheduleInfoResponse of(AlarmSchedule alarmSchedule) {
        List<AlarmInfoResponse> alarmInfoResponses = alarmSchedule.getAlarms().stream()
            .map(AlarmInfoResponse::of)
            .collect(Collectors.toList());
        return new AlarmScheduleInfoResponse(alarmSchedule.getId(), alarmSchedule.getType(), alarmSchedule.getDescription(), alarmInfoResponses);
    }

}
