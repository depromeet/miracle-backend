package com.depromeet.domain.record.repository;

import com.depromeet.domain.record.Record;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.domain.record.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Record findRecordById(Long id) {
        return queryFactory.selectFrom(record)
            .where(
                record.id.eq(id)
            ).fetchOne();
    }

}
