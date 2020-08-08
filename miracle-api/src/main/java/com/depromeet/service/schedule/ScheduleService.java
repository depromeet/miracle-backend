package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CreateScheduleResponse createSchedule(long memberId, CreateScheduleRequest request) {
        Schedule schedule = repository.save(request.toEntity(memberId));
        return CreateScheduleResponse.of(schedule);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getDailySchedule(long memberId, LocalDate date) {
        List<Schedule> schedules = repository.getSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(memberId, LoopType.NONE, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        schedules.addAll(repository.getSchedulesByMemberIdAndLoopType(memberId, LoopType.DAY));
        schedules.addAll(repository.getSchedulesByMemberIdAndLoopTypeAndDayOfWeek(memberId, LoopType.WEEK, date.getDayOfWeek()));
        schedules.addAll(repository.getSchedulesByMemberIdAndLoopTypeAndDay(memberId, LoopType.MONTH, date.getDayOfMonth()));
        return schedules
            .stream()
            .map(GetScheduleResponse::of)
            .collect(Collectors.toList());
    }
}
