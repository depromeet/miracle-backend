package com.depromeet.controller.record

import com.depromeet.ApiResponse
import com.depromeet.config.resolver.LoginMember
import com.depromeet.config.session.MemberSession
import com.depromeet.domain.record.Record
import com.depromeet.service.record.CreateRecordRequest
import com.depromeet.service.record.RecordService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RecordController(private val recordService: RecordService) {
    /**
     * 기록한다.
     *
     * @param member  가입 멤버 정보
     * @param request 등록하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @PostMapping(
        value = ["/api/v1/record"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createSchedule(
        @LoginMember member: MemberSession,
        @RequestBody request: CreateRecordRequest
    ): ApiResponse<Record> {
        return ApiResponse.of(
            recordService.createRecord(
                member.memberId,
                request
            )
        )
    }
}
