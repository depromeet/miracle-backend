package com.depromeet.service.schedule;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.ScheduleRepository;
import com.depromeet.service.schedule.dto.GetScheduleResponse;
import com.depromeet.service.schedule.dto.UpdateScheduleRequest;
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
            Arguments.of(InMemoryScheduleRepository.MEMBER_1, DayOfTheWeek.MON, Arrays.asList(1L, 2L, 3L, 4L)),
            Arguments.of(InMemoryScheduleRepository.MEMBER_2, DayOfTheWeek.MON, Arrays.asList(6L, 7L, 8L, 9L))
        );
    }

    @DisplayName("존재하지 않는 스케쥴 수정 시에 예외 발생")
    @Test
    void updateNotExistSchedule_ShouldFail() {
        UpdateScheduleRequest request = new UpdateScheduleRequest(1L, Category.EXERCISE, "desciption", DayOfTheWeek.MON, LocalTime.now(), LocalTime.now());
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
