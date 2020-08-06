package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.Schedule;

import java.time.LocalTime;

public class GetScheduleResponse {
    private long id;
    private int year;
    private int month;
    private int day;
    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String category;
    private String description;
    private String loopType;

    public GetScheduleResponse(long id, int year, int month, int day, int dayOfWeek, LocalTime startTime, LocalTime endTime, String category, String description, String loopType) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
        this.loopType = loopType;
    }

    public static GetScheduleResponse of(Schedule schedule) {
        return new GetScheduleResponse(schedule.getId(), schedule.getYear(), schedule.getMonth(), schedule.getDay(), schedule.getDayOfWeek().getValue(), schedule.getStartTime(), schedule.getEndTime(), schedule.getCategory(), schedule.getDescription(), schedule.getLoopType().getText());
    }

    public long getId() {
        return id;
    }
}
