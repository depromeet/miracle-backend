package com.depromeet.service.schedule.dto;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApiModel
public class CreateScheduleRequest {

    @NotBlank(message = "카테고리를 선택해주세요")
    private Category category;

    @NotBlank(message = "설명을 입력해주세요")
    @Length(max = 11, message = "11자 이하로 입력해주세요")
    private String description;

    @ApiModelProperty
    @NotNull(message = "요일들을 선택해주세요")
    private List<DayOfTheWeek> dayOfTheWeeks;

    @NotNull(message = "시작시간을 선택해주세요")
    private LocalTime startTime;

    @NotNull(message = "종료시간을 선택해주세요")
    private LocalTime endTime;

    public CreateScheduleRequest() {
        // needed by jackson
    }

    public CreateScheduleRequest(Category category, String description, List<DayOfTheWeek> dayOfTheWeeks, LocalTime startTime, LocalTime endTime) {
        this.category = category;
        this.description = description;
        this.dayOfTheWeeks = dayOfTheWeeks;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Schedule> toEntity(long memberId) {
        return dayOfTheWeeks.stream()
            .map(dayOfTheWeek -> Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime))
            .collect(Collectors.toList());
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<DayOfTheWeek> getDayOfTheWeeks() {
        return dayOfTheWeeks;
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

        CreateScheduleRequest that = (CreateScheduleRequest) o;

        if (category != that.category) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(dayOfTheWeeks, that.dayOfTheWeeks)) return false;
        if (!Objects.equals(startTime, that.startTime)) return false;
        return Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dayOfTheWeeks != null ? dayOfTheWeeks.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
