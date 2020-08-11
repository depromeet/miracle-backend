package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleRepository;
import com.deprommet.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AlarmServiceUtils {

    static AlarmSchedule findAlarmScheduleByIdAndMemberId(AlarmScheduleRepository alarmScheduleRepository, Long alarmScheduleId, Long memberId) {
        AlarmSchedule alarmSchedule = alarmScheduleRepository.findAlarmScheduleByIdAndMemberId(alarmScheduleId, memberId);
        if (alarmSchedule == null) {
            throw new NotFoundException(String.format("멤버 (%s) 에게 AlarmSchedule (%s) 은 존재하지 않습니다", memberId, alarmScheduleId), "해당 알람 스케쥴을 찾을 수 없습니다");
        }
        return alarmSchedule;
    }

}
