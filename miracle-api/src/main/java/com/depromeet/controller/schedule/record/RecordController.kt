package com.depromeet.controller.schedule.record

import com.depromeet.ApiResponse
import com.depromeet.config.resolver.LoginMember
import com.depromeet.config.session.MemberSession
import com.depromeet.service.schedule.CreateScheduleRequest
import com.depromeet.service.schedule.CreateScheduleResponse
import com.depromeet.service.schedule.record.CreateRecordRequest
import com.depromeet.service.schedule.record.RecordService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
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
    fun writeSchedule(
        @LoginMember member: MemberSession,
        @RequestBody request: CreateRecordRequest
    ) {
        ApiResponse.of(
            recordService.createRecord(
                member.memberId,
                request
            )
        )
    }
}
