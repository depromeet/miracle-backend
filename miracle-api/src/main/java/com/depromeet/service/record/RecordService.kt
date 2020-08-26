package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.record.RecordRepository
import com.depromeet.service.record.dto.request.CreateRecordRequest
import com.depromeet.service.record.dto.response.RecordResponse
import com.deprommet.exception.ConflictException
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
            throw ConflictException(String.format("이미 (%s)로 기록하였습니다.", record.id),"이미 기록하였습니다.")

    }
}
