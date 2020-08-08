package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleScheduleRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AlarmServiceUtils {

    static AlarmSchedule findAlarmScheduleByIdAndMemberId(AlarmScheduleScheduleRepository alarmScheduleScheduleRepository, Long alarmScheduleId, Long memberId) {
        AlarmSchedule alarmSchedule = alarmScheduleScheduleRepository.findAlarmScheduleByIdAndMemberId(alarmScheduleId, memberId);
        if (alarmSchedule == null) {
            throw new IllegalArgumentException(String.format("멤버 (%s) 에게 AlarmSchedule (%s) 은 존재하지 않습니다", memberId, alarmScheduleId));
        }
        return alarmSchedule;
    }

}