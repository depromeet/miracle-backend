package com.depromeet.domain.schedule.repository;

import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;

import java.util.List;

public interface ScheduleRepositoryCustom {

    List<Schedule> findSchedulesByMemberIdAndDayOfTheWeek(long memberId, DayOfTheWeek dayOfTheWeek);
}
