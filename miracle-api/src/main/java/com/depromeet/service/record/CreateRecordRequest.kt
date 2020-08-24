package com.depromeet.service.record

import com.depromeet.domain.common.Category
import com.depromeet.domain.record.Record
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.EnumType
import javax.persistence.Enumerated

class CreateRecordRequest(
    private var scheduleId: Long? = null,
    @field:Enumerated(EnumType.STRING)
    private val category: Category? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var startDateTime: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var endDateTime: LocalDateTime? = null,
    private var question: String? = null,
    private var answer: String? = null
) {
    fun toEntity(memberId: Long): Record {
        return Record.newInstance(memberId, scheduleId, category, startDateTime, endDateTime, question, answer)
    }
}
