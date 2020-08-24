package com.depromeet.domain.alarm.repository;

import com.depromeet.domain.alarm.AlarmSchedule;

import java.util.List;

public interface AlarmScheduleRepositoryCustom {

    List<AlarmSchedule> findAlarmSchedulesByMemberId(Long memberId);

    AlarmSchedule findAlarmScheduleByIdAndMemberId(Long id, Long memberId);

    List<AlarmSchedule> findWakeUpAlarmSchedulesByMemberId(Long memberId);

}
