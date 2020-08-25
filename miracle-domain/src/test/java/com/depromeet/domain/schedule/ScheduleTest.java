package com.depromeet.domain.schedule;

import com.depromeet.domain.common.Category;
import com.deprommet.exception.IllegalAccessException;
import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Schedule Entity Test")
class ScheduleTest {

    @DisplayName("Schedule 객체를 생성할 수 있다")
    @ParameterizedTest
    @MethodSource("source_createSchedule_ShouldSuccess")
    void createSchedule_ShouldSuccess(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        Schedule schedule = Schedule.of(memberId, startTime, endTime, category, description, loopType);
        assertAll(
            () -> assertThat(schedule.getYear()).isEqualTo(startTime.getYear()),
            () -> assertThat(schedule.getMonth()).isEqualTo(startTime.getMonthValue()),
            () -> assertThat(schedule.getDay()).isEqualTo(startTime.getDayOfMonth()),
            () -> assertThat(schedule.getStartTime()).isEqualTo(startTime.toLocalTime()),
            () -> assertThat(schedule.getEndTime()).isEqualTo(endTime.toLocalTime()),
            () -> assertThat(schedule.getLoopType()).isEqualTo(loopType)
        );
    }

    static Stream<Arguments> source_createSchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE),
            Arguments.of(2L, LocalDateTime.of(2020, 8, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), Category.EXERCISE, "스트레칭", LoopType.DAY),
            Arguments.of(3L, LocalDateTime.of(2020, 8, 6, 22, 0, 0), LocalDateTime.of(2020, 8, 6, 22, 30, 0), Category.DIARY, "취침시간", LoopType.WEEK)
        );
    }

    @DisplayName("시작시간이 종료시간 이후라면 예외 발생")
    @ParameterizedTest
    @MethodSource("source_startTimeAfterEndTime_ShouldFail")
    void startTimeAfterEndTime_ShouldFail(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        assertThatThrownBy(() -> {
            Schedule.of(memberId, startTime, endTime, category, description, loopType);
        }).isInstanceOf(ValidationException.class);
    }

    static Stream<Arguments> source_startTimeAfterEndTime_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 8, 30, 10), LocalDateTime.of(2020, 8, 6, 8, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE),
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 10, 10, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), Category.EXERCISE, "스트레칭", LoopType.DAY),
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 13, 30, 0), LocalDateTime.of(2020, 8, 6, 11, 30, 0), Category.DIARY, "취침시간", LoopType.WEEK)
        );
    }

    @DisplayName("시작시간과 종료시간이 같은 날짜가 아니라면 예외 발생")
    @ParameterizedTest
    @MethodSource("source_startTimeAndEndTimeNotSame_ShouldFail")
    void startTimeAndEndTimeNotSame_ShouldFail(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        assertThatThrownBy(() -> {
            Schedule.of(memberId, startTime, endTime, category, description, loopType);
        }).isInstanceOf(ValidationException.class);
    }

    static Stream<Arguments> source_startTimeAndEndTimeNotSame_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 7, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE),
            Arguments.of(1L, LocalDateTime.of(2020, 9, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), Category.EXERCISE, "스트레칭", LoopType.DAY),
            Arguments.of(1L, LocalDateTime.of(2030, 8, 6, 11, 0, 0), LocalDateTime.of(2020, 8, 6, 11, 30, 0), Category.DIARY, "취침시간", LoopType.WEEK)
        );
    }

    @DisplayName("Schedule 객체를 변경할 수 있다")
    @ParameterizedTest
    @MethodSource("source_updateSchedule_ShouldSuccess")
    void updateSchedule_ShouldSuccess(LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        Schedule schedule = Schedule.of(1L, LocalDateTime.of(2020, 8, 6, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE);
        schedule.update(schedule.getMemberId(), startTime, endTime, category, description, loopType);
        assertAll(
            () -> assertThat(schedule.getYear()).isEqualTo(startTime.getYear()),
            () -> assertThat(schedule.getMonth()).isEqualTo(startTime.getMonthValue()),
            () -> assertThat(schedule.getDay()).isEqualTo(startTime.getDayOfMonth()),
            () -> assertThat(schedule.getStartTime()).isEqualTo(startTime.toLocalTime()),
            () -> assertThat(schedule.getEndTime()).isEqualTo(endTime.toLocalTime()),
            () -> assertThat(schedule.getLoopType()).isEqualTo(loopType)
        );
    }

    static Stream<Arguments> source_updateSchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(LocalDateTime.of(2020, 8, 6, 6, 0, 0), LocalDateTime.of(2020, 8, 6, 6, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE),
            Arguments.of(LocalDateTime.of(2020, 8, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), Category.EXERCISE, "스트레칭-2", LoopType.DAY),
            Arguments.of(LocalDateTime.of(2020, 8, 6, 22, 0, 0), LocalDateTime.of(2020, 8, 6, 22, 30, 0), Category.DIARY, "취침시간", LoopType.MONTH)
        );
    }

    @DisplayName("다른 멤버의 스케쥴을 수정 시에 예외 발생")
    @ParameterizedTest
    @MethodSource("source_updateSchedule_ShouldFail")
    void updateSchedule_ShouldFail(LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        Schedule schedule = Schedule.of(1L, LocalDateTime.of(2020, 8, 6, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE);
        assertThatThrownBy(() -> {
            schedule.update(schedule.getMemberId() + 1, startTime, endTime, category, description, loopType);
        }).isInstanceOf(IllegalAccessException.class);
    }

    static Stream<Arguments> source_updateSchedule_ShouldFail() {
        return Stream.of(
            Arguments.of(LocalDateTime.of(2020, 8, 6, 6, 0, 0), LocalDateTime.of(2020, 8, 6, 6, 30, 0), Category.EXERCISE, "운동하기", LoopType.NONE),
            Arguments.of(LocalDateTime.of(2020, 8, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), Category.EXERCISE, "스트레칭-2", LoopType.DAY),
            Arguments.of(LocalDateTime.of(2020, 8, 6, 22, 0, 0), LocalDateTime.of(2020, 8, 6, 22, 30, 0), Category.DIARY, "취침시간", LoopType.MONTH)
        );
    }
}
