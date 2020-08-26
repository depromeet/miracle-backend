package com.depromeet.domain.record.repository;

import com.depromeet.domain.record.Record;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.depromeet.domain.record.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Record findByMemberIdAndScheduleIdAndStartTime(Long memberId, Long scheduleId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return queryFactory.selectFrom(record)
            .where(
                record.memberId.eq(memberId),
                record.scheduleId.eq(scheduleId),
                record.dateTimeInterval.startDateTime.eq(startDateTime),
                record.dateTimeInterval.endDateTime.eq(endDateTime)
            ).fetchOne();
    }

    @Override
    public List<Record> findRecordBetween(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return queryFactory.selectFrom(record)
            .where(
                record.dateTimeInterval.startDateTime.before(endDateTime),
                record.dateTimeInterval.endDateTime.after(startDateTime)
            ).fetch();
    }

}
