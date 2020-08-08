package com.depromeet.controller.alarm;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.alarm.AlarmService;
import com.depromeet.service.alarm.dto.request.CreateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.RetrieveAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.response.AlarmScheduleInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    /**
     * 새로운 알림 스케쥴을 생성하는 API
     */
    @PostMapping("/api/v1/alarm/schedule")
    public ApiResponse<AlarmScheduleInfoResponse> createAlarmSchedule(@Valid @RequestBody CreateAlarmScheduleRequest request,
                                                                      @LoginMember MemberSession memberSession) {
        return ApiResponse.of(alarmService.createAlarmSchedule(request, memberSession.getMemberId()));
    }

    /**
     * 회원의 알림 스케쥴 리스트를 불러오는 API
     */
    @GetMapping("/api/v1/alarm/schedule/my")
    public ApiResponse<List<AlarmScheduleInfoResponse>> retrieveAlarmSchedules(@LoginMember MemberSession memberSession) {
        return ApiResponse.of(alarmService.retrieveAlarmSchedules(memberSession.getMemberId()));
    }

    /**
     * 특정 앎림 스케쥴의 정보를 불러오는 API
     */
    @GetMapping("/api/v1/alarm/schedule")
    public ApiResponse<AlarmScheduleInfoResponse> retrieveAlarmSchedule(@Valid RetrieveAlarmScheduleRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(alarmService.retrieveAlarmSchedule(request, memberSession.getMemberId()));
    }

}
