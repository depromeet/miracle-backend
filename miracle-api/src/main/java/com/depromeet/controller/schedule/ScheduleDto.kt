package com.depromeet.controller.schedule

import com.depromeet.domain.common.Category
import com.depromeet.domain.common.DayOfTheWeek
import com.depromeet.domain.schedule.Schedule
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalTime
import java.util.stream.Collectors
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateScheduleRequest(
    @NotBlank(message = "카테고리를 선택해주세요")
    private var category: Category? = null,

    @NotBlank(message = "설명을 입력해주세요") @Length(max = 11, message = "11자 이하로 입력해주세요")
    private var description: String? = null,

    @ApiModelProperty
    @NotNull(message = "요일들을 선택해주세요")
    private var dayOfTheWeeks: List<DayOfTheWeek>? = null,

    @NotNull(message = "시작시간을 선택해주세요")
    private var startTime: LocalTime? = null,

    @NotNull(message = "종료시간을 선택해주세요")
    private var endTime: LocalTime? = null
) {
    fun toEntity(memberId: Long): List<Schedule>? {
        return dayOfTheWeeks?.stream()
            ?.map { dayOfTheWeek -> Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime) }
            ?.collect(Collectors.toList())
    }
}
