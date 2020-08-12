package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalTime;

@ApiModel
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
    @ApiModelProperty
    private LoopType loopType;

    public GetScheduleResponse(long id, int year, int month, int day, int dayOfWeek, LocalTime startTime, LocalTime endTime, String category, String description, LoopType loopType) {
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
        return new GetScheduleResponse(schedule.getId(), schedule.getYear(), schedule.getMonth(), schedule.getDay(), schedule.getDayOfWeek().getValue(), schedule.getStartTime(), schedule.getEndTime(), schedule.getCategory(), schedule.getDescription(), schedule.getLoopType());
    }

    public long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LoopType getLoopType() {
        return loopType;
    }
}
