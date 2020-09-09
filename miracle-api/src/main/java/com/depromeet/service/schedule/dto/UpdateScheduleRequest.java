package com.depromeet.service.schedule.dto;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class UpdateScheduleRequest {

    @NotNull(message = "수정하려는 스케쥴 id를 입력해주세요.")
    private Long scheduleId;

    @NotBlank(message = "카테고리를 선택해주세요")
    private Category category;

    @NotBlank(message = "설명을 입력해주세요")
    @Length(max = 11, message = "11자 이하로 입력해주세요")
    private String description;

    @ApiModelProperty
    @NotNull(message = "요일을 선택해주세요")
    private DayOfTheWeek dayOfTheWeek;

    @NotNull(message = "시작시간을 선택해주세요")
    private LocalTime startTime;

    @NotNull(message = "종료시간을 선택해주세요")
    private LocalTime endTime;

    public UpdateScheduleRequest() {
        // needed by jackson
    }

    public UpdateScheduleRequest(Long scheduleId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        this.scheduleId = scheduleId;
        this.category = category;
        this.description = description;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule toEntity(long memberId) {
        return Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime);
    }

    public Long getScheduleId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateScheduleRequest that = (UpdateScheduleRequest) o;

        if (scheduleId != null ? !scheduleId.equals(that.scheduleId) : that.scheduleId != null) return false;
        if (category != that.category) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dayOfTheWeek != that.dayOfTheWeek) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return endTime != null ? endTime.equals(that.endTime) : that.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = scheduleId != null ? scheduleId.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dayOfTheWeek != null ? dayOfTheWeek.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
