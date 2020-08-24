package com.depromeet.service.record

import com.depromeet.domain.record.Record

data class RecordListResponse(
    val list: List<Record>
) {
    companion object {
        @JvmStatic
        fun of(recordList: List<Record>): RecordListResponse {
            return RecordListResponse(recordList)
        }
    }
}
