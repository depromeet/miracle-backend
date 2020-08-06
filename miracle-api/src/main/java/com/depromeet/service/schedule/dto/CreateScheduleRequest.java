package com.depromeet.service.schedule.dto;

import com.depromeet.config.session.MemberSession;
import com.depromeet.domain.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CreateScheduleRequest {
    private long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private String category;
    private String description;
    private String loopType;

    public CreateScheduleRequest(LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
        this.loopType = loopType;
    }

    public Schedule toEntity() {
        return Schedule.of(memberId, startTime, endTime, category, description, loopType);
    }

    public void updateMemberInfo(MemberSession member) {
        this.memberId = member.getMemberId();
    }
}
