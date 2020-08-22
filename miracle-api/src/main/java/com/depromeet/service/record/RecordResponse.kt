package com.depromeet.service.record

import com.depromeet.domain.common.Category
import com.depromeet.domain.record.Record
import java.time.LocalDateTime
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class RecordResponse(
    val scheduleId: Long? = null,
    val startDateTime: LocalDateTime? = null,
    val endDateTime: LocalDateTime? = null,
    @field:Enumerated(EnumType.STRING)
    val category: Category? = null,
    val question: String? = null,
    val answer: String? = null
) {

    companion object {
        fun of(record: Record): RecordResponse {
            return RecordResponse(
                scheduleId = record.scheduleId,
                startDateTime = record.startDateTime,
                endDateTime =  record.endDateTime,
                category = record.category,
                question = record.question,
                answer = record.answer
            )
        }
    }
}



