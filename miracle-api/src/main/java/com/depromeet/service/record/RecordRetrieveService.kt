package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.record.RecordRepository
import com.depromeet.service.record.dto.request.DayRecordsRequest
import com.depromeet.service.record.dto.request.MonthRecordsRequest
import com.depromeet.service.record.dto.response.RecordForCalendarResponse
import com.depromeet.service.record.dto.response.RecordResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class RecordRetrieveService(private val recordRepository: RecordRepository) {

    @Transactional(readOnly = true)
    fun retrieveMonthRecords(request: MonthRecordsRequest, memberId: Long): RecordForCalendarResponse {
        val records = recordRepository.findRecordBetween(
            memberId,
            request.convertFirstDayOfTheMonth(),
            request.convertEndDayOfTheMonth()
        )
        return RecordForCalendarResponse.of(records)
    }

    @Transactional(readOnly = true)
    fun retrieveDayRecords(request: DayRecordsRequest, memberId: Long): List<RecordResponse> {
        val records = recordRepository.findRecordBetween(
            memberId,
            request.convertMinLocalDateTime(),
            request.convertMaxLocalDateTime()
        )
        return records.stream()
            .map { record: Record ->
                RecordResponse.of(
                    record
                )
            }
            .collect(Collectors.toList())
    }
}
