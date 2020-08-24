package com.depromeet.service.record

import com.depromeet.domain.record.Record
import java.time.LocalDate
import java.time.LocalDateTime

data class RecordListResponseForCalendar(
    val recordList: List<RecordForCalendarDisplay>
) {

    companion object {
        @JvmStatic
        fun of(list: List<Record>): RecordListResponseForCalendar {
            val recordList =
                if (list.isEmpty())
                    ArrayList()
                else {

                    list
                        .map {
                            TempRecordForCalendar(
                                date = convertToLocalDate(it.startDateTime),
                                id = it.id
                            )
                        }
                        .groupBy { it.date }
                        .map {
                            RecordForCalendarDisplay(it.key, it.value.map { it.id })
                        }
                        .sortedBy { it.date }

                }
            return RecordListResponseForCalendar(recordList)
        }

        @JvmStatic
        fun convertToLocalDate(dateTime: LocalDateTime): LocalDate {
            return LocalDate.of(dateTime.year, dateTime.month, dateTime.dayOfMonth)
        }
    }

}
