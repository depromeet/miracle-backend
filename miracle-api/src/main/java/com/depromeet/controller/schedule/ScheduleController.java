package com.depromeet.controller.schedule;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.schedule.ScheduleService;
import com.depromeet.service.schedule.dto.CreateScheduleRequest;
import com.depromeet.service.schedule.dto.CreateScheduleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/api/v1/schedule")
    public ApiResponse<CreateScheduleResponse> createSchedule(@LoginMember MemberSession member, @RequestBody CreateScheduleRequest request) {
        request.updateMemberInfo(member);
        return ApiResponse.of(scheduleService.createSchedule(request));
    }
}
