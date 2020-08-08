package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleScheduleRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AlarmServiceUtils {

    static AlarmSchedule findAlarmScheduleById(AlarmScheduleScheduleRepository alarmScheduleScheduleRepository, Long alarmScheduleId) {
        AlarmSchedule alarmSchedule = alarmScheduleScheduleRepository.findAlarmScheduleById(alarmScheduleId);
        if (alarmSchedule == null) {
            throw new IllegalArgumentException(String.format("id가 (%s)인 AlarmSchedule 은 존재하지 않습니다", alarmScheduleId));
        }
        return alarmSchedule;
    }

    static void validateOwner(AlarmSchedule alarmSchedule, Long memberId) {
        if (!alarmSchedule.isOwner(memberId)) {
            throw new IllegalArgumentException(String.format("AlarmSchedule (%s)은 멤버 (%s)의 알림 스케쥴이 아닙니다", alarmSchedule.getId(), memberId));
        }
    }
}
