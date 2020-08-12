package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.LoopType;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryScheduleRepository implements ScheduleRepository {

    public static final long MEMBER_1 = 1L;
    public static final long MEMBER_2 = 2L;
    private final List<Schedule> schedules = new ArrayList<>();
    private long currentId = 1L;

    public InMemoryScheduleRepository() {
        schedules.add(generateSchedule(MEMBER_1, LocalDateTime.of(2020, 8, 8, 6, 0), LocalDateTime.of(2020, 8, 8, 6, 30), "기상", "기상하기", LoopType.NONE.getText()));
        schedules.add(generateSchedule(MEMBER_1, LocalDateTime.of(2020, 8, 8, 6, 0), LocalDateTime.of(2020, 8, 8, 6, 30), "기상", "기상하기", LoopType.DAY.getText()));
        schedules.add(generateSchedule(MEMBER_1, LocalDateTime.of(2020, 8, 1, 6, 0), LocalDateTime.of(2020, 8, 1, 6, 30), "기상", "기상하기", LoopType.WEEK.getText()));
        schedules.add(generateSchedule(MEMBER_1, LocalDateTime.of(2020, 9, 8, 6, 0), LocalDateTime.of(2020, 9, 8, 6, 30), "기상", "기상하기", LoopType.MONTH.getText()));
        schedules.add(generateSchedule(MEMBER_1, LocalDateTime.of(2020, 9, 9, 6, 0), LocalDateTime.of(2020, 9, 9, 6, 30), "취침", "취침하기", LoopType.NONE.getText()));
        schedules.add(generateSchedule(MEMBER_2, LocalDateTime.of(2020, 7, 10, 20, 0), LocalDateTime.of(2020, 7, 10, 21, 0), "취침", "취침하기", LoopType.NONE.getText()));
        schedules.add(generateSchedule(MEMBER_2, LocalDateTime.of(2020, 7, 10, 20, 0), LocalDateTime.of(2020, 7, 10, 21, 0), "취침", "취침하기", LoopType.DAY.getText()));
        schedules.add(generateSchedule(MEMBER_2, LocalDateTime.of(2020, 7, 17, 20, 0), LocalDateTime.of(2020, 7, 17, 21, 0), "취침", "취침하기", LoopType.WEEK.getText()));
        schedules.add(generateSchedule(MEMBER_2, LocalDateTime.of(2020, 6, 10, 20, 0), LocalDateTime.of(2020, 6, 10, 21, 0), "취침", "취침하기", LoopType.MONTH.getText()));
        schedules.add(generateSchedule(MEMBER_2, LocalDateTime.of(2020, 9, 10, 20, 0), LocalDateTime.of(2020, 9, 10, 21, 0), "기상", "기상하기", LoopType.WEEK.getText()));
    }

    private Schedule generateSchedule(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, String loopType) {
        Schedule schedule = Schedule.of(memberId, startTime, endTime, category, description, loopType);
        try {
        Class clazz = Class.forName("com.depromeet.domain.schedule.Schedule");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(schedule, currentId++);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
        return schedule;
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndYearAndMonthAndDay(long memberId, LoopType loopType, int year, int month, int day) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> s.getLoopType().equals(loopType))
            .filter(s -> s.getYear() == year)
            .filter(s -> s.getMonth() == month)
            .filter(s -> s.getDay() == day)
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopType(long memberId, LoopType loopType) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> s.getLoopType().equals(loopType))
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDayOfWeek(long memberId, LoopType loopType, DayOfWeek dayOfWeek) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> s.getLoopType().equals(loopType))
            .filter(s -> s.getDayOfWeek().equals(dayOfWeek))
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndLoopTypeAndDay(long memberId, LoopType loopType, int day) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> s.getLoopType().equals(loopType))
            .filter(s -> s.getDay() == day)
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAll() {
        return null;
    }

    @Override
    public List<Schedule> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Schedule> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Schedule> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Schedule> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Schedule getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Schedule> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Schedule> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Schedule> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Schedule> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Schedule entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Schedule> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Schedule> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Schedule> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Schedule> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Schedule> boolean exists(Example<S> example) {
        return false;
    }
}
