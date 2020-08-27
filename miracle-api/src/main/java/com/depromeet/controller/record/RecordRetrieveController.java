package com.depromeet.controller.record;

import com.depromeet.ApiResponse;
import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import com.depromeet.service.record.RecordRetrieveService;
import com.depromeet.service.record.dto.request.DayRecordsRequest;
import com.depromeet.service.record.dto.request.MonthRecordsRequest;
import com.depromeet.service.record.dto.response.RecordForCalendarResponse;
import com.depromeet.service.record.dto.response.RecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecordRetrieveController {

    private final RecordRetrieveService recordRetrieveService;

    @GetMapping("/api/v1/record/month")
    public ApiResponse<RecordForCalendarResponse> retrieveMonthRecords(@Valid MonthRecordsRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(recordRetrieveService.retrieveMonthRecords(request, memberSession.getMemberId()));
    }

    @GetMapping("/api/v1/record/day")
    public ApiResponse<List<RecordResponse>> retrieveDayRecords(@Valid DayRecordsRequest request, @LoginMember MemberSession memberSession) {
        return ApiResponse.of(recordRetrieveService.retrieveDayRecords(request, memberSession.getMemberId()));
    }
}
