package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.record.RecordRepository
import com.depromeet.domain.schedule.InvalidScheduleTimeException
import com.depromeet.domain.schedule.record.Record
import com.depromeet.domain.schedule.record.RecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RecordService(private val repository: RecordRepository) {

    @Transactional
    fun createRecord(memberId: Long, request: CreateRecordRequest): Record {
        val record: Record = request.toEntity(memberId)
        val result = repository.findByMemberIdAndScheduleIdAndStartTime(
            memberId,
            record.scheduleId,
            LocalDateTime.now()
        )
        if (result == null)
            return repository.save(record)
        else
            throw Exception("이미 기록하였습니다.")
    }
}
