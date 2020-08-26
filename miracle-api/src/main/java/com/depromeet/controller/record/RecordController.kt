package com.depromeet.controller.record

import com.depromeet.ApiResponse
import com.depromeet.config.resolver.LoginMember
import com.depromeet.config.session.MemberSession
import com.depromeet.service.record.*
import com.depromeet.service.record.dto.request.CreateRecordRequest
import com.depromeet.service.record.dto.response.RecordResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class RecordController(private val recordService: RecordService) {

    @PostMapping(
        value = ["/api/v1/record"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createRecord(
        @LoginMember member: MemberSession,
        @RequestBody request: CreateRecordRequest
    ): ApiResponse<RecordResponse> {
        return ApiResponse.of(
            recordService.createRecord(
                member.memberId,
                request
            )
        )
    }

}
