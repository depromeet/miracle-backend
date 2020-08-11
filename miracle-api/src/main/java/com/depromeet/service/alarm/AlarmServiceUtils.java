package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleRepository;
import com.deprommet.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AlarmServiceUtils {

    static AlarmSchedule findAlarmScheduleByIdAndMemberId(AlarmScheduleRepository alarmScheduleRepository, Long alarmScheduleId, Long memberId) {
        AlarmSchedule alarmSchedule = alarmScheduleRepository.findAlarmScheduleByIdAndMemberId(alarmScheduleId, memberId);
        if (alarmSchedule == null) {
            log.info(String.format("멤버 (%s) 에게 AlarmSchedule (%s) 은 존재하지 않습니다", memberId, alarmScheduleId));
            throw new NotFoundException("해당 알람 스케쥴을 찾을 수 없습니다");
        }
        return alarmSchedule;
    }

}
