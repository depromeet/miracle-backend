package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import com.depromeet.service.schedule.dto.*;
import com.deprommet.exception.IllegalAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    /**
     * 스케쥴을 데이터 베이스에 저장한다
     *
     * @param memberId 가입 멤버 ID
     * @param request  등록하고자 하는 스케쥴 정보
     * @return 스케쥴 ID
     */
    @Transactional
    public CreateScheduleResponse createSchedule(long memberId, CreateScheduleRequest request) {
        Schedule schedule = repository.save(request.toEntity(memberId));
        return CreateScheduleResponse.of(schedule);
    }

    /**
     * 특정 날짜의 전체 스케쥴을 조회한다.
     *
     * @param memberId 가입 멤버 ID
     * @param date     조회 날짜
     * @return 해당 날짜에 등록된 전체 스케쥴 정보
     */
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> retrieveDailySchedule(long memberId, LocalDate date) {
        List<Schedule> schedules = repository.findSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(memberId, LoopType.NONE, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        schedules.addAll(repository.findSchedulesByMemberIdAndLoopType(memberId, LoopType.DAY));
        schedules.addAll(repository.findSchedulesByMemberIdAndLoopTypeAndDayOfWeek(memberId, LoopType.WEEK, date.getDayOfWeek()));
        schedules.addAll(repository.findSchedulesByMemberIdAndLoopTypeAndDay(memberId, LoopType.MONTH, date.getDayOfMonth()));
        return schedules
            .stream()
            .map(GetScheduleResponse::of)
            .collect(Collectors.toList());
    }

    /**
     * 데이터 베이스의 스케쥴을 수정한다.
     *
     * @param memberId 가입 멤버 ID
     * @param scheduleId 수정하고자 하는 스케쥴 ID
     * @param request  수정하고자 하는 스케쥴 정보
     * @return
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(long memberId, long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = repository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException(String.format("스케쥴 (%d)은 존재하지 않습니다", scheduleId)));
        schedule.update(memberId, request.getStartTime(), request.getEndTime(), request.getCategory(), request.getDescription(), request.getLoopType());
        return UpdateScheduleResponse.of(schedule);
    }

    /**
     * 데이터 베이스의 스케쥴을 삭제한다.
     *
     * @param memberId 가입 멤버 ID
     * @param scheduleId  삭제하고자 하는 스케쥴 ID
     */
    public void deleteSchedule(long memberId, long scheduleId) {
        Schedule schedule = repository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException(String.format("스케쥴 (%d)은 존재하지 않습니다", scheduleId)));

        if (schedule.getMemberId() != memberId) {
            throw new IllegalAccessException(String.format("스케쥴 (%d)은 삭제할 수 없습니다", scheduleId), "스케쥴은 삭제할 수 없습니다");
        }

        repository.delete(schedule);
    }
}
