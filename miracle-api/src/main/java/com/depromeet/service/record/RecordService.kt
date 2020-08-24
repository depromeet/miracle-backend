package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.record.RecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


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

    fun getRecordListForCalendar(memberId: Long, date: LocalDate): RecordListResponseForCalendar {
        val result = repository.findByMonth(memberId, Record.makeStartDateTime(date), Record.makeEndDateTime(date))
        return RecordListResponseForCalendar.of(result)
    }

    fun getRecordListForLocalDate(memberId: Long, date: LocalDate): RecordListResponse {
        val result = repository.findByDayOfMonth(memberId, Record.makeStartDateTime(date), Record.makeEndDateTime(date))
        return RecordListResponse.of(result)
    }
}
