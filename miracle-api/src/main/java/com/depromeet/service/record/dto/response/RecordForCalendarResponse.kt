package com.depromeet.service.record.dto.response

import com.depromeet.domain.record.Record

class RecordForCalendarResponse(
    val dateList: List<Int>
) {
    companion object {
        @JvmStatic
        fun of(records: List<Record>): RecordForCalendarResponse {
            return RecordForCalendarResponse(records.map { it.startDateTime.dayOfMonth }.toSortedSet().toList())
        }
    }
}
