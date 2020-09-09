package com.depromeet.service.schedule.dto;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import io.swagger.annotations.ApiModel;

import java.time.LocalTime;

@ApiModel
public class GetScheduleResponse {
    private long scheduleId;
    private Category category;
    private String description;
    private DayOfTheWeek dayOfTheWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public GetScheduleResponse(long scheduleId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        this.scheduleId = scheduleId;
        this.category = category;
        this.description = description;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static GetScheduleResponse of(Schedule schedule) {
        return new GetScheduleResponse(schedule.getId(), schedule.getCategory(), schedule.getDescription(), schedule.getDayOfTheWeek(), schedule.getStartTime(), schedule.getEndTime());
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
