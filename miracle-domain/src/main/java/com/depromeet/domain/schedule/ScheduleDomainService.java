package com.depromeet.domain.schedule;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.deprommet.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class ScheduleDomainService {
    private final ScheduleRepository repository;

    public ScheduleDomainService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public List<Schedule> createSchedules(long memberId, Category category, String description, List<DayOfTheWeek> dayOfTheWeeks, LocalTime startTime, LocalTime endTime) {
        List<Schedule> schedules = repository.findSchedulesByMemberIdAndDayOfTheWeeks(memberId, dayOfTheWeeks);

        validateTimePeriod(startTime, endTime, schedules);
        List<Schedule> newSchedules = dayOfTheWeeks.stream()
            .map(dayOfTheWeek -> Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime))
            .collect(Collectors.toList());
        return repository.saveAll(newSchedules);
    }

    public Schedule updateSchedule(long scheduleId, Schedule newSchedule) {
        List<Schedule> otherSchedules = repository.findSchedulesByMemberIdAndDayOfTheWeek(newSchedule.getMemberId(), newSchedule.getDayOfTheWeek()).stream()
            .filter(s -> s.getId() != scheduleId)
            .collect(Collectors.toList());;

        validateTimePeriod(newSchedule.getStartTime(), newSchedule.getEndTime(), otherSchedules);

        Schedule schedule = repository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException(String.format("스케쥴 (%d)은 존재하지 않습니다", scheduleId)));
        schedule.update(newSchedule.getMemberId(), newSchedule.getCategory(), newSchedule.getDescription(), newSchedule.getDayOfTheWeek(), newSchedule.getStartTime(), newSchedule.getEndTime());
        return schedule;
    }

    private void validateTimePeriod(LocalTime startTime, LocalTime endTime, List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            if (schedule.getStartTime().compareTo(startTime) > 0 && schedule.getStartTime().compareTo(endTime) < 0) {
                throw new ValidationException("다른 스케쥴과 시간이 겹칠 수 없습니다.", "다른 스케쥴과 시간이 겹칠 수 없습니다.");
            }
            if (schedule.getEndTime().compareTo(startTime) > 0 && schedule.getEndTime().compareTo(endTime) < 0) {
                throw new ValidationException("다른 스케쥴과 시간이 겹칠 수 없습니다.", "다른 스케쥴과 시간이 겹칠 수 없습니다.");
            }
        }
    }
}
