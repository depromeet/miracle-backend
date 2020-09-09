package com.depromeet.service.schedule;

import com.depromeet.controller.schedule.CreateScheduleRequest;
import com.depromeet.controller.schedule.GetScheduleResponse;
import com.depromeet.controller.schedule.UpdateScheduleRequest;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.ScheduleRepository;
import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Schedule Service Test")
class ScheduleServiceTest {

    private ScheduleService service;
    private ScheduleRepository repository = new InMemoryScheduleRepository();

    public ScheduleServiceTest() {
        this.service = new ScheduleService(repository);
    }

    @DisplayName("하루 동안의 스케쥴을 조회 할 수 있다")
    @ParameterizedTest
    @MethodSource("source_retrieveDailySchedule_ShouldSuccess")
    void retrieveDailySchedule_ShouldSuccess(long memberId, DayOfTheWeek dayOfTheWeek, List<Long> scheduleIds) {
        List<GetScheduleResponse> response = service.retrieveDailySchedule(memberId, dayOfTheWeek);

        List<Long> result = response
            .stream()
            .map(GetScheduleResponse::getScheduleId)
            .sorted(Long::compareTo)
            .collect(Collectors.toList());

        assertThat(scheduleIds).isEqualTo(result);
    }

    static Stream<Arguments> source_retrieveDailySchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(InMemoryScheduleRepository.MEMBER_1, DayOfTheWeek.MON, Arrays.asList(1L)),
            Arguments.of(InMemoryScheduleRepository.MEMBER_2, DayOfTheWeek.MON, Arrays.asList(6L))
        );
    }

    @DisplayName("겹치는 요일, 시간으로 스케쥴 등록 시에 예외 발생")
    @ParameterizedTest
    @MethodSource("source_createDuplicateSchedule_ShouldFail")
    void createDuplicateSchedule_ShouldFail(LocalTime startTime, LocalTime endTime) {
        CreateScheduleRequest request = new CreateScheduleRequest(Category.EXERCISE, "desciption", Arrays.asList(DayOfTheWeek.MON), startTime, endTime);
        assertThatThrownBy(() -> {
            service.createSchedule(InMemoryScheduleRepository.MEMBER_1, request);
        }).isInstanceOf(ValidationException.class);
    }

    static Stream<Arguments> source_createDuplicateSchedule_ShouldFail() {
        return Stream.of(
            Arguments.of(LocalTime.of(5, 30), LocalTime.of(6, 10)),
            Arguments.of(LocalTime.of(5, 30), LocalTime.of(6, 30)),
            Arguments.of(LocalTime.of(5, 30), LocalTime.of(6, 40)),
            Arguments.of(LocalTime.of(6, 0), LocalTime.of(6, 40)),
            Arguments.of(LocalTime.of(6, 10), LocalTime.of(6, 40)),
            Arguments.of(LocalTime.of(6, 20), LocalTime.of(7, 0))
        );
    }

    @DisplayName("존재하지 않는 스케쥴 수정 시에 예외 발생")
    @Test
    void updateNotExistSchedule_ShouldFail() {
        UpdateScheduleRequest request = new UpdateScheduleRequest(Category.EXERCISE, "desciption", DayOfTheWeek.MON, LocalTime.now(), LocalTime.now());
        assertThatThrownBy(() -> {
            service.updateSchedule(InMemoryScheduleRepository.MEMBER_1, 0L, request);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("스케쥴을 삭제할 수 있다")
    @Test
    void deleteSchedule_ShouldSuccess() {
        int expected = repository.findAll().size() - 1;

        service.deleteSchedule(InMemoryScheduleRepository.MEMBER_1, 1L);

        assertThat(repository.findAll().size()).isEqualTo(expected);
    }
}
