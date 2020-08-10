package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.Schedule;

public class UpdateScheduleResponse {
    private long scheduleId;

    private UpdateScheduleResponse(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public static UpdateScheduleResponse of(Schedule schedule) {
        return new UpdateScheduleResponse(schedule.getId());
    }

    public long getScheduleId() {
        return scheduleId;
    }
}
