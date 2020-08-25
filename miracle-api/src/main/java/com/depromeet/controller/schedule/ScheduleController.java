package com.depromeet.controller.schedule;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.schedule.ScheduleService;
import com.depromeet.service.schedule.dto.*;
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
     * @param session 가입 멤버 정보
     * @param request 등록하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @PostMapping(value = "/api/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CreateScheduleResponse> createSchedule(@LoginMember MemberSession session, @RequestBody CreateScheduleRequest request) {
        return ApiResponse.of(scheduleService.createSchedule(session.getMemberId(), request));
    }

    /**
     * 특정 날짜의 전체 스케쥴을 조회한다.
     *
     * @param session 가입 멤버 정보
     * @param year    조회 년도
     * @param month   조회 월
     * @param day     조회 날짜
     * @return 해당 날짜에 등록된 전체 스케쥴 정보
     */
    @GetMapping(value = "/api/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List<GetScheduleResponse>> retrieveSchedule(@LoginMember MemberSession session, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ApiResponse.of(scheduleService.retrieveDailySchedule(session.getMemberId(), LocalDate.of(year, month, day)));
    }

    /**
     * 스케쥴을 수정한다.
     *
     * @param session 가입 멤버 정보
     * @param scheduleId 수정하고자 하는 스케쥴 ID
     * @param request 수정하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @PutMapping(value = "/api/v1/schedule/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UpdateScheduleResponse> updateSchedule(@LoginMember MemberSession session, @PathVariable long scheduleId, @RequestBody UpdateScheduleRequest request) {
        return ApiResponse.of(scheduleService.updateSchedule(session.getMemberId(), scheduleId, request));
    }

    /**
     * 스케쥴을 삭제한다.
     *
     * @param session 가입 멤버 정보
     * @param scheduleId  삭제하고자 하는 스케쥴 ID
     * @return
     */
    @DeleteMapping(value = "/api/v1/schedule/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteSchedule(@LoginMember MemberSession session, @PathVariable long scheduleId) {
        scheduleService.deleteSchedule(session.getMemberId(), scheduleId);
        return ApiResponse.SUCCESS;
    }
}
