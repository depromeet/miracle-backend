package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class CreateScheduleResponse {
    private List<Long> schedulesId;

    public CreateScheduleResponse(List<Long> schedulesId) {
        this.schedulesId = schedulesId;
    }

    public static CreateScheduleResponse of(List<Schedule> schedules) {
        return new CreateScheduleResponse(schedules.stream()
            .map(Schedule::getId)
            .collect(Collectors.toList()));
    }

    public List<Long> getSchedulesId() {
        return schedulesId;
    }
}
