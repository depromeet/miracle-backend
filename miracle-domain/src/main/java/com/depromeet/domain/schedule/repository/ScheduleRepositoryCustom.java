package com.depromeet.domain.schedule.repository;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> getSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(long memberId, LoopType loopType, int year, int month, int day);

    List<Schedule> getSchedulesByMemberIdAndLoopType(long memberId, LoopType loopType);

    List<Schedule> getSchedulesByMemberIdAndLoopTypeAndDayOfWeek(long memberId, LoopType loopType, DayOfWeek dayOfWeek);

    List<Schedule> getSchedulesByMemberIdAndLoopTypeAndDay(long memberId, LoopType loopType, int day);
}
