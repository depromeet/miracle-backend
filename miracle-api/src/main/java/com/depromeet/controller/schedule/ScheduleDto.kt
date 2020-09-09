package com.depromeet.controller.schedule

import com.depromeet.domain.common.Category
import com.depromeet.domain.common.DayOfTheWeek
import com.depromeet.domain.schedule.Schedule
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalTime
import java.util.stream.Collectors
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateScheduleRequest(
    @NotBlank(message = "카테고리를 선택해주세요")
    var category: Category? = null,

    @NotBlank(message = "설명을 입력해주세요") @Length(max = 11, message = "11자 이하로 입력해주세요")
    var description: String? = null,

    @ApiModelProperty
    @NotNull(message = "요일들을 선택해주세요")
    var dayOfTheWeeks: List<DayOfTheWeek>? = null,

    @NotNull(message = "시작시간을 선택해주세요")
    var startTime: LocalTime? = null,

    @NotNull(message = "종료시간을 선택해주세요")
    var endTime: LocalTime? = null
) {
    fun toEntity(memberId: Long): List<Schedule>? {
        return dayOfTheWeeks?.stream()
            ?.map { dayOfTheWeek -> Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime) }
            ?.collect(Collectors.toList())
    }
}

data class CreateScheduleResponse(
    val scheduleIds: List<Long>
) {
    companion object {
        @JvmStatic
        fun of(schedules: List<Schedule>): CreateScheduleResponse {
            return CreateScheduleResponse(schedules.stream()
                .map { obj: Schedule -> obj.id }
                .collect(Collectors.toList()))
        }
    }
}

data class GetCategoryComment(
    val comment: String
)

@ApiModel
data class GetScheduleResponse(
    val scheduleId: Long,
    val category: Category,
    val description: String,
    val dayOfTheWeek: DayOfTheWeek,
    val startTime: LocalTime,
    val endTime: LocalTime
) {
    companion object {
        @JvmStatic
        fun of(schedule: Schedule): GetScheduleResponse {
            return GetScheduleResponse(schedule.id, schedule.category, schedule.description, schedule.dayOfTheWeek, schedule.startTime, schedule.endTime)
        }
    }
}

data class UpdateScheduleRequest(
    @NotBlank(message = "카테고리를 선택해주세요")
    var category: Category? = null,

    @NotBlank(message = "설명을 입력해주세요")
    @Length(max = 11, message = "11자 이하로 입력해주세요")
    var description: String? = null,

    @NotNull(message = "요일을 선택해주세요")
    @ApiModelProperty
    var dayOfTheWeek: DayOfTheWeek? = null,

    @NotNull(message = "시작시간을 선택해주세요")
    var startTime: LocalTime? = null,

    @NotNull(message = "종료시간을 선택해주세요")
    var endTime: LocalTime? = null
) {
    fun toEntity(memberId: Long): Schedule {
        return Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime)
    }
}

class UpdateScheduleResponse(
    val scheduleId: Long
) {
    companion object {
        @JvmStatic
        fun of(schedule: Schedule): UpdateScheduleResponse {
            return UpdateScheduleResponse(schedule.id)
        }
    }
}
