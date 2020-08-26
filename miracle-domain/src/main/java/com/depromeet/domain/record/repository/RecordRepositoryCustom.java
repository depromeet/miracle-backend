package com.depromeet.domain.record.repository;

import com.depromeet.domain.record.Record;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepositoryCustom {

    Record findByMemberIdAndScheduleIdAndStartTime(Long memberId, Long scheduleId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Record> findRecordBetween(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
