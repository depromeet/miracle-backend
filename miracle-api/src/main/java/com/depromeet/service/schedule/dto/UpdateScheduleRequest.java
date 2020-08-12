package com.depromeet.service.schedule.dto;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

public class UpdateScheduleRequest {

    @NotBlank(message = "시작시간을 선택해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @NotBlank(message = "종료시간을 선택해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @NotBlank(message = "카테고리를 선택해주세요")
    private String category;

    @NotBlank(message = "설명을 입력해주세요")
    @Length(max = 11, message = "11자 이하로 입력해주세요")
    private String description;

    @ApiModelProperty
    @NotBlank(message = "반복설정을 선택해주세요")
    private LoopType loopType;

    public UpdateScheduleRequest() {
        // needed by jackson
    }

    public UpdateScheduleRequest(LocalDateTime startTime, LocalDateTime endTime, String category, String description, LoopType loopType) {
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

    public LoopType getLoopType() {
        return loopType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateScheduleRequest request = (UpdateScheduleRequest) o;
        return Objects.equals(startTime, request.startTime) &&
            Objects.equals(endTime, request.endTime) &&
            Objects.equals(category, request.category) &&
            Objects.equals(description, request.description) &&
            Objects.equals(loopType, request.loopType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, category, description, loopType);
    }
}
