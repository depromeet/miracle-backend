package com.depromeet.service.record

import java.time.LocalDate


data class RecordForCalendarDisplay(
    val date: LocalDate,
    val recordIdList: List<Long>
)
