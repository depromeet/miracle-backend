package com.depromeet.domain.alarm.repository;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.domain.alarm.QAlarm.alarm;
import static com.depromeet.domain.alarm.QAlarmSchedule.alarmSchedule;

@RequiredArgsConstructor
public class AlarmScheduleRepositoryCustomImpl implements AlarmScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlarmSchedule> findAlarmSchedulesByMemberId(Long memberId) {
        return queryFactory.selectFrom(alarmSchedule).distinct()
            .leftJoin(alarmSchedule.alarms, alarm).fetchJoin()
            .where(
                alarmSchedule.memberId.eq(memberId)
            ).fetch();
    }

    @Override
    public AlarmSchedule findAlarmScheduleByIdAndMemberId(Long id, Long memberId) {
        return queryFactory.selectFrom(alarmSchedule).distinct()
            .leftJoin(alarmSchedule.alarms, alarm).fetchJoin()
            .where(
                alarmSchedule.id.eq(id),
                alarmSchedule.memberId.eq(memberId)
            ).fetchOne();
    }

}
