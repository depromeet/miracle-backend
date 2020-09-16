package com.depromeet.service.schedule;

import com.depromeet.controller.schedule.*;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleDomainService;
import com.depromeet.domain.schedule.ScheduleRepository;
import com.deprommet.exception.IllegalAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleDomainService scheduleDomainService;
    private final ScheduleRepository repository;

    public ScheduleService(ScheduleDomainService scheduleDomainService, ScheduleRepository repository) {
        this.scheduleDomainService = scheduleDomainService;
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
        return CreateScheduleResponse.of(scheduleDomainService.createSchedules(memberId, request.getCategory(), request.getDescription(), request.getDayOfTheWeeks(), request.getStartTime(), request.getEndTime()));
    }

    /**
     * 특정 요일의 전체 스케쥴을 조회한다.
     *
     * @param memberId     가입 멤버 ID
     * @param dayOfTheWeek 조회 요일
     * @return 해당 요일에 등록된 전체 스케쥴 정보
     */
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> retrieveDailySchedule(long memberId, DayOfTheWeek dayOfTheWeek) {
        List<Schedule> schedules = repository.findSchedulesByMemberIdAndDayOfTheWeek(memberId, dayOfTheWeek);
        return schedules
            .stream()
            .map(GetScheduleResponse::of)
            .collect(Collectors.toList());
    }

    /**
     * 데이터 베이스의 스케쥴을 수정한다.
     *
     * @param memberId   가입 멤버 ID
     * @param scheduleId 수정하고자 하는 스케쥴 ID
     * @param request    수정하고자 하는 스케쥴 정보
     * @return
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(long memberId, long scheduleId, UpdateScheduleRequest request) {
        return UpdateScheduleResponse.of(scheduleDomainService.updateSchedule(scheduleId, request.toEntity(memberId)));
    }

    /**
     * 데이터 베이스의 스케쥴을 삭제한다.
     *
     * @param memberId   가입 멤버 ID
     * @param scheduleId 삭제하고자 하는 스케쥴 ID
     */
    @Transactional
    public void deleteSchedule(long memberId, long scheduleId) {
        Schedule schedule = repository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException(String.format("스케쥴 (%d)은 존재하지 않습니다", scheduleId)));

        if (schedule.getMemberId() != memberId) {
            throw new IllegalAccessException(String.format("스케쥴 (%d)은 삭제할 수 없습니다", scheduleId), "스케쥴은 삭제할 수 없습니다");
        }

        repository.delete(schedule);
    }

    /**
     * 스케쥴 인증을 위해 카테고리별 인증 코멘트를 조회한다.
     *
     * @param memberId   가입 멤버 ID
     * @param scheduleId 인증 코멘트가 필요한 스케쥴 ID
     * @return 인증 화면에 노출될 코멘트
     */
    @Transactional(readOnly = true)
    public GetCategoryComment getCategoryComment(Long memberId, long scheduleId) {
        Schedule schedule = repository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException(String.format("스케쥴 (%d)은 존재하지 않습니다", scheduleId)));

        if (schedule.getMemberId() != memberId) {
            throw new IllegalAccessException(String.format("스케쥴 (%d)에 접근할 수 없습니다.", scheduleId), "스케쥴에 접근할 수 없습니다.");
        }

        return new GetCategoryComment(schedule.getCategory().retrieveRecordComment());
    }
}
