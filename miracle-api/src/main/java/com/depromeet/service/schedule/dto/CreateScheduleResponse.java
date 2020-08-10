package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.Schedule;

public class CreateScheduleResponse {
    private long scheduleId;

    private CreateScheduleResponse(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public static CreateScheduleResponse of(Schedule schedule) {
        return new CreateScheduleResponse(schedule.getId());
    }

    public long getScheduleId() {
        return scheduleId;
    }
}
