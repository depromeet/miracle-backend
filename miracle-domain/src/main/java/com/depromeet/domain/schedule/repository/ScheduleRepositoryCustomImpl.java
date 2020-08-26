package com.depromeet.domain.schedule.repository;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

import static com.depromeet.domain.schedule.QSchedule.schedule;

@Repository
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(long memberId, LoopType loopType, int year, int month, int day) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.loopType.eq(loopType),
                schedule.year.eq(year),
                schedule.month.eq(month),
                schedule.day.eq(day)
            ).fetch();
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopType(long memberId, LoopType loopType) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.loopType.eq(loopType)
            ).fetch();
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDayOfWeek(long memberId, LoopType loopType, DayOfWeek dayOfWeek) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.loopType.eq(loopType),
                schedule.dayOfWeek.eq(dayOfWeek)
            ).fetch();
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDay(long memberId, LoopType loopType, int day) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.loopType.eq(loopType),
                schedule.day.eq(day)
            ).fetch();
    }

    @Override
    public Schedule findScheduleByIdAndMemberId(Long scheduleId, Long memberId) {
        return queryFactory.selectFrom(schedule)
            .where(
                schedule.memberId.eq(memberId),
                schedule.id.eq(scheduleId)
            ).fetchOne();
    }
}
