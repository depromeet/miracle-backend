package com.depromeet.domain.record.repository

import com.depromeet.domain.record.QRecord.record
import com.depromeet.domain.record.Record
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

class RecordRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : RecordRepositoryCustom {

    override fun findByMemberIdAndScheduleIdAndStartTime(
        memberId: Long?,
        scheduleId: Long?,
        startTimeAt: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): Record? {
        return queryFactory.selectFrom(record)
            .where(
                record.memberId.eq(memberId),
                record.scheduleId.eq(scheduleId),
                record.startDateTime.eq(startTimeAt),
                record.endDateTime.eq(endDateTime)
            ).fetchOne()
    }

    override fun findByMonth(
        memberId: Long?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): MutableList<Record> {

        return queryFactory.selectFrom(record)
            .where(
                record.startDateTime.after(startDateTime),
                record.startDateTime.before(endDateTime)
            ).fetch()

    }

    override fun findByDayOfMonth(
        memberId: Long?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?
    ): MutableList<Record> {
        return queryFactory.selectFrom(record)
            .where(
                record.startDateTime.after(startDateTime),
                record.startDateTime.before(endDateTime)
            ).fetch()
    }
}
