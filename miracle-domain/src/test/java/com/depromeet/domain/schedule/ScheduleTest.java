package com.depromeet.domain.schedule;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.deprommet.exception.IllegalAccessException;
import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Schedule Entity Test")
class ScheduleTest {

    @DisplayName("Schedule 객체를 생성할 수 있다")
    @ParameterizedTest
    @MethodSource("source_createSchedule_ShouldSuccess")
    void createSchedule_ShouldSuccess(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime);
        assertAll(
            () -> assertThat(schedule.getCategory()).isEqualTo(category),
            () -> assertThat(schedule.getDescription()).isEqualTo(description),
            () -> assertThat(schedule.getDayOfTheWeek()).isEqualTo(dayOfTheWeek),
            () -> assertThat(schedule.getStartTime()).isEqualTo(startTime),
            () -> assertThat(schedule.getEndTime()).isEqualTo(endTime)
        );
    }

    static Stream<Arguments> source_createSchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(1L, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(8, 0), LocalTime.of(8, 30)),
            Arguments.of(2L, Category.EXERCISE, "스트레칭", DayOfTheWeek.THU, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            Arguments.of(3L, Category.DIARY, "취침시간", DayOfTheWeek.WED, LocalTime.of(22, 0), LocalTime.of(22, 30))
        );
    }

    @DisplayName("시작시간이 종료시간 이후라면 예외 발생")
    @ParameterizedTest
    @MethodSource("source_startTimeAfterEndTime_ShouldFail")
    void startTimeAfterEndTime_ShouldFail(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        assertThatThrownBy(() -> {
            Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime);
        }).isInstanceOf(ValidationException.class);
    }

    static Stream<Arguments> source_startTimeAfterEndTime_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(9, 0), LocalTime.of(8, 30)),
            Arguments.of(2L, Category.EXERCISE, "스트레칭", DayOfTheWeek.THU, LocalTime.of(12, 0), LocalTime.of(10, 0)),
            Arguments.of(3L, Category.DIARY, "취침시간", DayOfTheWeek.WED, LocalTime.of(23, 0), LocalTime.of(22, 30))
        );
    }

    @DisplayName("Schedule 객체를 변경할 수 있다")
    @ParameterizedTest
    @MethodSource("source_updateSchedule_ShouldSuccess")
    void updateSchedule_ShouldSuccess(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = Schedule.of(memberId, Category.MEDITATION, "독서하기", DayOfTheWeek.MON, LocalTime.of(8, 0), LocalTime.of(8, 30));
        schedule.update(schedule.getMemberId(), category, description, dayOfTheWeek, startTime, endTime);
        assertAll(
            () -> assertThat(schedule.getCategory()).isEqualTo(category),
            () -> assertThat(schedule.getDescription()).isEqualTo(description),
            () -> assertThat(schedule.getDayOfTheWeek()).isEqualTo(dayOfTheWeek),
            () -> assertThat(schedule.getStartTime()).isEqualTo(startTime),
            () -> assertThat(schedule.getEndTime()).isEqualTo(endTime)
        );
    }

    static Stream<Arguments> source_updateSchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(1L, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(8, 0), LocalTime.of(8, 30)),
            Arguments.of(2L, Category.EXERCISE, "스트레칭", DayOfTheWeek.THU, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            Arguments.of(3L, Category.DIARY, "취침시간", DayOfTheWeek.WED, LocalTime.of(22, 0), LocalTime.of(22, 30))
        );
    }

    @DisplayName("다른 멤버의 스케쥴을 수정 시에 예외 발생")
    @ParameterizedTest
    @MethodSource("source_updateSchedule_ShouldFail")
    void updateSchedule_ShouldFail(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = Schedule.of(memberId, Category.MEDITATION, "독서하기", DayOfTheWeek.MON, LocalTime.of(8, 0), LocalTime.of(8, 30));
        assertThatThrownBy(() -> {
            schedule.update(schedule.getMemberId() + 1, category, description, dayOfTheWeek, startTime, endTime);
        }).isInstanceOf(IllegalAccessException.class);
    }

    static Stream<Arguments> source_updateSchedule_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(8, 0), LocalTime.of(8, 30)),
            Arguments.of(2L, Category.EXERCISE, "스트레칭", DayOfTheWeek.THU, LocalTime.of(9, 0), LocalTime.of(10, 0)),
            Arguments.of(3L, Category.DIARY, "취침시간", DayOfTheWeek.WED, LocalTime.of(22, 0), LocalTime.of(22, 30))
        );
    }
}
