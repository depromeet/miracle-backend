package com.depromeet.domain.schedule;

import com.depromeet.domain.BaseTimeEntity;
import com.deprommet.exception.IllegalAccessException;
import com.deprommet.exception.ValidationException;
import com.depromeet.domain.common.Category;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long memberId;

    private int year;

    private int month;

    private int day;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @Enumerated(EnumType.STRING)
    private LoopType loopType;

    public Schedule() {
        //needed by hibernate
    }

    public Schedule(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        validateTime(startTime, endTime);
        this.memberId = memberId;
        this.year = startTime.getYear();
        this.month = startTime.getMonthValue();
        this.day = startTime.getDayOfMonth();
        this.dayOfWeek = startTime.getDayOfWeek();
        this.startTime = startTime.toLocalTime();
        this.endTime = endTime.toLocalTime();
        this.category = category;
        this.description = description;
        this.loopType = loopType;
    }

    public static Schedule of(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        return new Schedule(memberId, startTime, endTime, category, description, loopType);
    }

    private void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            throw new ValidationException("날짜 형식은 'yyyy-MM-dd'이여야 합니다.", "날짜 형식은 'yyyy-MM-dd'이여야 합니다.");
        }
        if (startTime.isAfter(endTime)) {
            throw new ValidationException("시작 시간은 종료 시간보다 반드시 먼저여야 합니다.", "시작 시간은 종료 시간보다 반드시 먼저여야 합니다.");
        }
    }

    public void update(long memberId, LocalDateTime startTime, LocalDateTime endTime, Category category, String description, LoopType loopType) {
        if (this.memberId != memberId) {
            throw new IllegalAccessException(String.format("타인의 스케쥴 (%d)은 수정할 수 없습니다", this.id), "타인의 스케쥴은 수정할 수 없습니다");
        }

        validateTime(startTime, endTime);
        this.year = startTime.getYear();
        this.month = startTime.getMonthValue();
        this.day = startTime.getDayOfMonth();
        this.dayOfWeek = startTime.getDayOfWeek();
        this.startTime = startTime.toLocalTime();
        this.endTime = endTime.toLocalTime();
        this.category = category;
        this.description = description;
        this.loopType = loopType;
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LoopType getLoopType() {
        return loopType;
    }
}
