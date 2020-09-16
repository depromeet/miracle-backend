package com.depromeet.service.schedule;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryScheduleRepository implements ScheduleRepository {

    public static final long MEMBER_1 = 1L;
    public static final long MEMBER_2 = 2L;
    private List<Schedule> schedules = new ArrayList<>();
    private long currentId = 1L;

    public InMemoryScheduleRepository() {
        schedules.add(generateSchedule(MEMBER_1, Category.EXERCISE, "운동하기", DayOfTheWeek.MON, LocalTime.of(6, 0), LocalTime.of(6, 30)));
        schedules.add(generateSchedule(MEMBER_1, Category.EXERCISE, "운동하기", DayOfTheWeek.TUE, LocalTime.of(6, 0), LocalTime.of(6, 30)));
        schedules.add(generateSchedule(MEMBER_1, Category.EXERCISE, "운동하기", DayOfTheWeek.WED, LocalTime.of(6, 0), LocalTime.of(6, 30)));
        schedules.add(generateSchedule(MEMBER_1, Category.EXERCISE, "운동하기", DayOfTheWeek.THU, LocalTime.of(6, 0), LocalTime.of(6, 30)));
        schedules.add(generateSchedule(MEMBER_1, Category.READING, "책읽기", DayOfTheWeek.FRI, LocalTime.of(6, 0), LocalTime.of(6, 30)));
        schedules.add(generateSchedule(MEMBER_2, Category.READING, "책읽기", DayOfTheWeek.MON, LocalTime.of(20, 0), LocalTime.of(21, 0)));
        schedules.add(generateSchedule(MEMBER_2, Category.READING, "책읽기", DayOfTheWeek.TUE, LocalTime.of(20, 0), LocalTime.of(21, 0)));
        schedules.add(generateSchedule(MEMBER_2, Category.READING, "책읽기", DayOfTheWeek.WED, LocalTime.of(20, 0), LocalTime.of(21, 0)));
        schedules.add(generateSchedule(MEMBER_2, Category.READING, "책읽기", DayOfTheWeek.THU, LocalTime.of(20, 0), LocalTime.of(21, 0)));
        schedules.add(generateSchedule(MEMBER_2, Category.EXERCISE, "운동하기", DayOfTheWeek.FRI, LocalTime.of(20, 0), LocalTime.of(21, 0)));
    }

    private Schedule generateSchedule(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = Schedule.of(memberId, category, description, dayOfTheWeek, startTime, endTime);
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
    public List<Schedule> findSchedulesByMemberIdAndDayOfTheWeek(long memberId, DayOfTheWeek dayOfTheWeek) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> s.getDayOfTheWeek().equals(dayOfTheWeek))
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findSchedulesByMemberIdAndDayOfTheWeeks(long memberId, List<DayOfTheWeek> dayOfTheWeeks) {
        return schedules
            .stream()
            .filter(s -> s.getMemberId() == memberId)
            .filter(s -> dayOfTheWeeks.contains(s.getDayOfTheWeek()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAll() {
        return schedules;
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
        List<S> result = (List<S>)entities;
        for (Schedule schedule : result) {
            try {
                Class clazz = Class.forName("com.depromeet.domain.schedule.Schedule");
                Field field = clazz.getDeclaredField("id");
                field.setAccessible(true);
                field.set(schedule, currentId++);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                return null;
            }
        }
        schedules.addAll(result);
        return result;
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
        return schedules
            .stream()
            .filter(s -> s.getId().equals(aLong))
            .findFirst();
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
        schedules = schedules
            .stream()
            .filter(s -> !s.getId().equals(entity.getId()))
            .collect(Collectors.toList());
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
