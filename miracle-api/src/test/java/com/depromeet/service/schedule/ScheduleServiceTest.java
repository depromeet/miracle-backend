package com.depromeet.service.schedule;

import com.depromeet.service.schedule.dto.GetScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Schedule Service Test")
class ScheduleServiceTest {

    private ScheduleService service;

    public ScheduleServiceTest() {
        this.service = new ScheduleService(new InMemoryScheduleRepository());
    }

    @DisplayName("하루 동안의 스케쥴을 조회 할 수 있다")
    @ParameterizedTest
    @MethodSource("source_getDailySchedule_ShouldSuccess")
    void getDailySchedule_ShouldSuccess(long memberId, LocalDate date, List<Long> scheduleIds) {
        List<GetScheduleResponse> response = service.getDailySchedule(memberId, date);

        List<Long> result = response
            .stream()
            .map(GetScheduleResponse::getId)
            .sorted(Long::compareTo)
            .collect(Collectors.toList());

        assertThat(scheduleIds.equals(result)).isTrue();
    }

    static Stream<Arguments> source_getDailySchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(InMemoryScheduleRepository.MEMBER_1, LocalDate.of(2020, 8, 8), Arrays.asList(1L, 2L, 3L, 4L)),
            Arguments.of(InMemoryScheduleRepository.MEMBER_2, LocalDate.of(2020, 7, 10), Arrays.asList(6L, 7L, 8L, 9L))
        );
    }
}
