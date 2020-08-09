package com.depromeet.controller.schedule;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.schedule.CreateScheduleRequest;
import com.depromeet.service.schedule.CreateScheduleResponse;
import com.depromeet.service.schedule.GetScheduleResponse;
import com.depromeet.service.schedule.ScheduleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 스케쥴을 등록한다.
     *
     * @param member  가입 멤버 정보
     * @param request 등록하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @PostMapping(value = "/api/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CreateScheduleResponse> createSchedule(@LoginMember MemberSession member, @RequestBody CreateScheduleRequest request) {
        return ApiResponse.of(scheduleService.createSchedule(member.getMemberId(), request));
    }

    /**
     * 특정 날짜의 전체 스케쥴을 조회한다.
     *
     * @param member 가입 멤버 정보
     * @param year   조회 년도
     * @param month  조회 월
     * @param day    조회 날짜
     * @return 해당 날짜에 등록된 전체 스케쥴 정보
     */
    @GetMapping(value = "/api/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List<GetScheduleResponse>> getSchedule(@LoginMember MemberSession member, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ApiResponse.of(scheduleService.getDailySchedule(member.getMemberId(), LocalDate.of(year, month, day)));
    }
}
