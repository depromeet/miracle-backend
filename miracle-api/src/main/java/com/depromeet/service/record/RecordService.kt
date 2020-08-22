package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.record.RecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordService(private val repository: RecordRepository) {

    @Transactional
    fun createRecord(memberId: Long, request: CreateRecordRequest): RecordResponse {
        val record: Record = request.toEntity(memberId)
        val result = repository.findByMemberIdAndScheduleIdAndStartTime(
            memberId,
            record.scheduleId,
            record.startDateTime,
            record.endDateTime
        )
        if (result == null)
            return RecordResponse.of(repository.save(record))
        else
            throw Exception("이미 기록하였습니다.")
    }
}
