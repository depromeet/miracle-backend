package com.depromeet.service.schedule.record

import com.depromeet.domain.schedule.InvalidScheduleTimeException
import com.depromeet.domain.schedule.record.Record
import com.depromeet.domain.schedule.record.RecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordService(private val repository: RecordRepository) {

    @Transactional
    fun createRecord(memberId: Long, request: CreateRecordRequest): Record {
        val record: Record = request.toEntity(memberId)
        val result = repository.findByMemberIdAndScheduleIdAndYearAndMonthAndDay(
            record.memberId,
            record.scheduleId,
            record.year,
            record.month,
            record.day
        )
        if (result == null)
            return repository.save(record)
        else
            throw Exception("이미 기록하였습니다.")
    }
}
