package com.depromeet.domain.record.repository;

import com.depromeet.domain.record.Record;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepositoryCustom {

    Record findRecordById(Long id);

    List<Record> findByMemberIdAndScheduleIdAndStartTime(Long memberId,
                                                         Long scheduleId,
                                                         LocalDateTime startDateTime);


}
