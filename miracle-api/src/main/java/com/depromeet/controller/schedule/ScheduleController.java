package com.depromeet.controller.schedule;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.service.schedule.ScheduleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
     * 특정 요일의 전체 스케쥴을 조회한다.
     *
     * @param session      가입 멤버 정보
     * @param dayOfTheWeek 조회 요일
     * @return 해당 요일에 등록된 전체 스케쥴 정보
     */
    @GetMapping(value = "/api/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List<GetScheduleResponse>> retrieveSchedule(@LoginMember MemberSession session, @RequestParam DayOfTheWeek dayOfTheWeek) {
        return ApiResponse.of(scheduleService.retrieveDailySchedule(session.getMemberId(), dayOfTheWeek));
    }

    /**
     * 스케쥴을 수정한다.
     *
     * @param session    가입 멤버 정보
     * @param scheduleId 수정하고자 하는 스케쥴 ID
     * @param request    수정하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @PutMapping(value = "/api/v1/schedule/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UpdateScheduleResponse> updateSchedule(@LoginMember MemberSession session, @PathVariable long scheduleId, @RequestBody UpdateScheduleRequest request) {
        return ApiResponse.of(scheduleService.updateSchedule(session.getMemberId(), scheduleId, request));
    }

    /**
     * 스케쥴을 삭제한다.
     *
     * @param session    가입 멤버 정보
     * @param scheduleId 삭제하고자 하는 스케쥴 ID
     * @return
     */
    @DeleteMapping(value = "/api/v1/schedule/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteSchedule(@LoginMember MemberSession session, @PathVariable long scheduleId) {
        scheduleService.deleteSchedule(session.getMemberId(), scheduleId);
        return ApiResponse.SUCCESS;
    }

    @GetMapping(value = "/api/v1/schedule/{scheduleId}/category/comment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<GetCategoryComment> getCategoryComment(@LoginMember MemberSession session, @PathVariable long scheduleId) {
        return ApiResponse.of(scheduleService.getCategoryComment(session.getMemberId(), scheduleId));
    }
}
