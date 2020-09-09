package com.depromeet.domain.schedule;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DayOfTheWeek;
import com.deprommet.exception.IllegalAccessException;
import com.deprommet.exception.ValidationException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long memberId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    public Schedule() {
        //needed by hibernate
    }

    public Schedule(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        this.memberId = memberId;
        this.category = category;
        this.description = description;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule of(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        validateTime(startTime, endTime);
        return new Schedule(memberId, category, description, dayOfTheWeek, startTime, endTime);
    }

    public void update(long memberId, Category category, String description, DayOfTheWeek dayOfTheWeek, LocalTime startTime, LocalTime endTime) {
        if (this.memberId != memberId) {
            throw new IllegalAccessException(String.format("타인의 스케쥴 (%d)은 수정할 수 없습니다", this.id), "타인의 스케쥴은 수정할 수 없습니다");
        }
        validateTime(startTime, endTime);
        this.category = category;
        this.description = description;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private static void validateTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new ValidationException("시작 시간은 종료 시간보다 반드시 먼저여야 합니다.", "시작 시간은 종료 시간보다 반드시 먼저여야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
