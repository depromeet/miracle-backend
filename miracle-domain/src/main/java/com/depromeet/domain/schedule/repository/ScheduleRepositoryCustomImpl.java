package com.depromeet.domain.schedule.repository;

import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.depromeet.domain.schedule.QSchedule.schedule;

@Repository
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndDayOfTheWeek(long memberId, DayOfTheWeek dayOfTheWeek) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.dayOfTheWeek.eq(dayOfTheWeek)
            ).fetch();
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndDayOfTheWeeks(long memberId, List<DayOfTheWeek> dayOfTheWeeks) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.dayOfTheWeek.in(dayOfTheWeeks)
            ).fetch();
    }
}
