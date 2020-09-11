package com.depromeet.domain.schedule;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ScheduleDomainService Test")
@SpringBootTest
class ScheduleDomainServiceTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private ScheduleDomainService scheduleDomainService;

    @BeforeEach
    void setup() {
        this.scheduleDomainService = new ScheduleDomainService(scheduleRepository);
    }

    @DisplayName("스케줄 등록 시에 다른 스케줄과 시간이 겹치면 예외 발생")
    @Test
    void createDuplicateTimeSchedules_ShouldFail() {
        scheduleDomainService.createSchedules(1L, Category.EXERCISE, "운동하기", Arrays.asList(DayOfTheWeek.MON, DayOfTheWeek.THU), LocalTime.of(8, 0), LocalTime.of(9, 0));
        assertThatThrownBy(() -> {
            scheduleDomainService.createSchedules(1L, Category.EXERCISE, "운동하기", Arrays.asList(DayOfTheWeek.MON), LocalTime.of(8, 20), LocalTime.of(9, 30));
        }).isInstanceOf(ValidationException.class);
    }

    @DisplayName("스케줄 수정 시에 다른 스케줄과 시간이 겹치면 예외 발생")
    @Test
    void updateDuplicateTimeSchedules_ShouldFail() {
        List<Schedule> otherSchedules = scheduleDomainService.createSchedules(1L, Category.EXERCISE, "운동하기", Arrays.asList(DayOfTheWeek.MON), LocalTime.of(8, 0), LocalTime.of(9, 0));
        List<Schedule> newSchedules = scheduleDomainService.createSchedules(1L, Category.EXERCISE, "운동하기", Arrays.asList(DayOfTheWeek.THU), LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertThatThrownBy(() -> {
            scheduleDomainService.updateSchedule(newSchedules.get(0).getId(), Schedule.of(1L, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(8, 20), LocalTime.of(9, 30)));
        }).isInstanceOf(ValidationException.class);
    }
}
