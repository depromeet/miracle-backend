package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UpdateScheduleRequest {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private String category;
    private String description;
    private String loopType;

    public UpdateScheduleRequest(LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
        this.loopType = loopType;
    }

    public Schedule toEntity(long memberId) {
        return Schedule.of(memberId, startTime, endTime, category, description, loopType);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLoopType() {
        return loopType;
    }
}
