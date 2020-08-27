package com.depromeet.service.record.dto.response

import com.depromeet.domain.record.Record
import java.time.LocalDate
import java.util.*

class RecordForCalendarResponse(
    val dateList: List<LocalDate>
) {
    companion object {
        @JvmStatic
        fun of(records: List<Record>): RecordForCalendarResponse {
            return RecordForCalendarResponse(records.map { it.startDateTime.toLocalDate() }.toSortedSet().toList())
        }
    }
}
