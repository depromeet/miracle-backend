package com.depromeet.domain.schedule.repository;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(long memberId, LoopType loopType, int year, int month, int day);

    List<Schedule> findSchedulesByMemberIdAndLoopType(long memberId, LoopType loopType);

    List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDayOfWeek(long memberId, LoopType loopType, DayOfWeek dayOfWeek);

    List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDay(long memberId, LoopType loopType, int day);

    Schedule findScheduleByIdAndMemberId(Long scheduleId, Long memberId);
}
