package com.depromeet.service.record

import com.depromeet.domain.record.Record
import com.depromeet.domain.schedule.record.Record
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class CreateRecordRequest(
    private var scheduleId: Long? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var startDateTime: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var endDateTime: LocalDateTime? = null,
    private var question: String? = null,
    private var answer: String? = null
) {

    fun toEntity(memberId: Long): Record {
        return Record.newInstance(memberId, scheduleId!!, startDateTime, endDateTime, question, answer)
    }
}
