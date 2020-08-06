package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.repository.ScheduleRepository;
import com.depromeet.service.schedule.dto.CreateScheduleRequest;
import com.depromeet.service.schedule.dto.CreateScheduleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        Schedule schedule = repository.save(request.toEntity());
        return CreateScheduleResponse.of(schedule);
    }
}
