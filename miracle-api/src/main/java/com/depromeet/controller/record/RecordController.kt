package com.depromeet.controller.record

import com.depromeet.ApiResponse
import com.depromeet.config.resolver.LoginMember
import com.depromeet.config.session.MemberSession
import com.depromeet.service.record.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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

    @GetMapping(
        value = ["/api/v1/record/month"]
    )
    fun getRecordListForCalendar(
        @LoginMember member: MemberSession,
        @RequestParam(value = "month") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): ApiResponse<RecordListResponseForCalendar> {
        return ApiResponse.of(
            recordService.getRecordListForCalendar(member.memberId, date)
        )
    }

    @GetMapping(
        value = ["/api/v1/record/today"]
    )
    fun getRecordListForLocalDate(
        @LoginMember member: MemberSession,
        @RequestParam(value = "today") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): ApiResponse<RecordListResponse> {
        return ApiResponse.of(
            recordService.getRecordListForLocalDate(member.memberId, date)
        )
    }

}
