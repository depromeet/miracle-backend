package com.depromeet.domain.schedule.record

import com.querydsl.jpa.impl.JPAQueryFactory


class RecordRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : RecordRepositoryCustom {

    override fun findByMemberIdAndScheduleIdAndYearAndMonthAndDay(
        memberId: Long,
        scheduleId: Long,
        year: Int,
        month: Int,
        day: Int
    ): MutableList<Record>? {
        return null
    }

//        return queryFactory.selectFrom(record)
//            .where(
//                record.memberId.eq(memberId),
//                record.scheduleId.eq(scheduleId),
//                record.year.eq(year),
//                record.month.eq(month),
//                record.day.eq(day)
//            ).fetch()

}
