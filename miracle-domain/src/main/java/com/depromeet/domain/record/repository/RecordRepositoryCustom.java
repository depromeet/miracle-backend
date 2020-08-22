package com.depromeet.domain.record.repository;

import com.depromeet.domain.record.Record;

import java.time.LocalDateTime;

public interface RecordRepositoryCustom {

    Record findRecordById(Long id);

    Record findByMemberIdAndScheduleIdAndStartTime(Long memberId,
                                                   Long scheduleId,
                                                   LocalDateTime startDateTime);
    
}
