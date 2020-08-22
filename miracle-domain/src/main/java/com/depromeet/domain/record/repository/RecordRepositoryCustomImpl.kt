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
            .where(record.memberId.eq(memberId))
            .where(record.scheduleId.eq(scheduleId))
            .where(record.startDateTime.eq(startTimeAt))
            .where(record.endDateTime.eq(endDateTime))
            .fetchOne()
    }
}
