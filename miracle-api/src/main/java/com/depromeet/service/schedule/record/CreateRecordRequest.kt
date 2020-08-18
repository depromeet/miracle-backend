package com.depromeet.service.schedule.record

import com.depromeet.domain.schedule.Schedule
import com.depromeet.domain.schedule.record.Record
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.LocalTime

class CreateRecordRequest(
    private var scheduleId: Long? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var time: LocalDateTime? = null,
    private var content: String? = null
) {

    fun toEntity(memberId: Long): Record {
        return Record.of(memberId, scheduleId!!, time, content)
    }
}
