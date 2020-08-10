package com.depromeet.domain.schedule;

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
    void createSchedule_ShouldSuccess(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        Schedule schedule = Schedule.of(memberId, startTime, endTime, category, description, loopType);
        assertAll(
            () -> assertThat(schedule.getYear()).isEqualTo(startTime.getYear()),
            () -> assertThat(schedule.getMonth()).isEqualTo(startTime.getMonthValue()),
            () -> assertThat(schedule.getDay()).isEqualTo(startTime.getDayOfMonth()),
            () -> assertThat(schedule.getStartTime()).isEqualTo(startTime.toLocalTime()),
            () -> assertThat(schedule.getEndTime()).isEqualTo(endTime.toLocalTime()),
            () -> assertThat(schedule.getLoopType()).isEqualTo(LoopType.of(loopType))
        );
    }

    static Stream<Arguments> source_createSchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), "운동", "운동하기", "NONE"),
            Arguments.of(2L, LocalDateTime.of(2020, 8, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), "요가", "스트레칭", "DAY"),
            Arguments.of(3L, LocalDateTime.of(2020, 8, 6, 22, 0, 0), LocalDateTime.of(2020, 8, 6, 22, 30, 0), "수면", "취침시간", "WEEK")
        );
    }

    @DisplayName("시작시간이 종료시간 이후라면 예외 발생")
    @ParameterizedTest
    @MethodSource("source_startTimeAfterEndTime_ShouldFail")
    void startTimeAfterEndTime_ShouldFail(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        assertThatThrownBy(() -> {
            Schedule.of(memberId, startTime, endTime, category, description, loopType);
        }).isInstanceOf(InvalidScheduleTimeException.class);
    }

    static Stream<Arguments> source_startTimeAfterEndTime_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 8, 30, 10), LocalDateTime.of(2020, 8, 6, 8, 30, 0), "운동", "운동하기", "NONE"),
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 10, 10, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), "운동", "스트레칭", "DAY"),
            Arguments.of(1L, LocalDateTime.of(2020, 8, 6, 13, 30, 0), LocalDateTime.of(2020, 8, 6, 11, 30, 0), "수면", "취침시간", "WEEK")
        );
    }

    @DisplayName("시작시간과 종료시간이 같은 날짜가 아니라면 예외 발생")
    @ParameterizedTest
    @MethodSource("source_startTimeAndEndTimeNotSame_ShouldFail")
    void startTimeAndEndTimeNotSame_ShouldFail(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        assertThatThrownBy(() -> {
            Schedule.of(memberId, startTime, endTime, category, description, loopType);
        }).isInstanceOf(InvalidScheduleTimeException.class);
    }

    static Stream<Arguments> source_startTimeAndEndTimeNotSame_ShouldFail() {
        return Stream.of(
            Arguments.of(1L, LocalDateTime.of(2020, 8, 7, 8, 0, 0), LocalDateTime.of(2020, 8, 6, 8, 30, 0), "운동", "운동하기", "NONE"),
            Arguments.of(1L, LocalDateTime.of(2020, 9, 6, 9, 0, 0), LocalDateTime.of(2020, 8, 6, 10, 0, 0), "운동", "스트레칭", "DAY"),
            Arguments.of(1L, LocalDateTime.of(2030, 8, 6, 11, 0, 0), LocalDateTime.of(2020, 8, 6, 11, 30, 0), "수면", "취침시간", "WEEK")
        );
    }
}
