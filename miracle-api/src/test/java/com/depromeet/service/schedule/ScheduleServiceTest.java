package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Schedule Service Test")
@SpringBootTest
class ScheduleServiceTest {

    public static final long MEMBER_1 = 1L;
    public static final long MEMBER_2 = 2L;
    private ScheduleService service;

    @Autowired
    private ScheduleRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new ScheduleService(repository);
        repository.save(Schedule.of(MEMBER_1, LocalDateTime.of(2020, 8, 8, 6, 0), LocalDateTime.of(2020, 8, 8, 6, 30), "기상", "기상하기", LoopType.NONE.getText()));
        repository.save(Schedule.of(MEMBER_1, LocalDateTime.of(2020, 8, 8, 6, 0), LocalDateTime.of(2020, 8, 8, 6, 30), "기상", "기상하기", LoopType.DAY.getText()));
        repository.save(Schedule.of(MEMBER_1, LocalDateTime.of(2020, 8, 1, 6, 0), LocalDateTime.of(2020, 8, 1, 6, 30), "기상", "기상하기", LoopType.WEEK.getText()));
        repository.save(Schedule.of(MEMBER_1, LocalDateTime.of(2020, 9, 8, 6, 0), LocalDateTime.of(2020, 9, 8, 6, 30), "기상", "기상하기", LoopType.MONTH.getText()));
        repository.save(Schedule.of(MEMBER_1, LocalDateTime.of(2020, 9, 9, 6, 0), LocalDateTime.of(2020, 9, 9, 6, 30), "취침", "취침하기", LoopType.NONE.getText()));
        repository.save(Schedule.of(MEMBER_2, LocalDateTime.of(2020, 7, 10, 20, 0), LocalDateTime.of(2020, 7, 10, 21, 0), "취침", "취침하기", LoopType.NONE.getText()));
        repository.save(Schedule.of(MEMBER_2, LocalDateTime.of(2020, 7, 10, 20, 0), LocalDateTime.of(2020, 7, 10, 21, 0), "취침", "취침하기", LoopType.DAY.getText()));
        repository.save(Schedule.of(MEMBER_2, LocalDateTime.of(2020, 7, 17, 20, 0), LocalDateTime.of(2020, 7, 17, 21, 0), "취침", "취침하기", LoopType.WEEK.getText()));
        repository.save(Schedule.of(MEMBER_2, LocalDateTime.of(2020, 6, 10, 20, 0), LocalDateTime.of(2020, 6, 10, 21, 0), "취침", "취침하기", LoopType.MONTH.getText()));
        repository.save(Schedule.of(MEMBER_2, LocalDateTime.of(2020, 9, 10, 20, 0), LocalDateTime.of(2020, 9, 10, 21, 0), "기상", "기상하기", LoopType.WEEK.getText()));
    }

    @AfterEach
    public void clean() {
        repository.deleteAll();
    }

    @DisplayName("하루 동안의 스케쥴을 조회 할 수 있다")
    @ParameterizedTest
    @MethodSource("source_getDailySchedule_ShouldSuccess")
    public void getDailySchedule_ShouldSuccess(long memberId, LocalDate date, List<Long> scheduleIds) {
        List<GetScheduleResponse> response = service.getDailySchedule(memberId, date);

        List<Long> result = response
            .stream()
            .map(GetScheduleResponse::getId)
            .sorted(Long::compareTo)
            .collect(Collectors.toList());

        assertThat(scheduleIds.equals(result)).isTrue();
    }

    public static Stream<Arguments> source_getDailySchedule_ShouldSuccess() {
        return Stream.of(
            Arguments.of(MEMBER_1, LocalDate.of(2020, 8, 8), Arrays.asList(1L, 2L, 3L, 4L)),
            Arguments.of(MEMBER_2, LocalDate.of(2020, 7, 10), Arrays.asList(16L, 17L, 18L, 19L))
        );
    }
}
