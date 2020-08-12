package com.depromeet.domain.schedule;

import com.depromeet.domain.BaseTimeEntity;

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

    private String category;

    private String description;

    @Enumerated(EnumType.STRING)
    private LoopType loopType;

    public Schedule() {
        //needed by hibernate
    }

    public Schedule(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, LoopType loopType) {
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

    public static Schedule of(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, LoopType loopType) {
        return new Schedule(memberId, startTime, endTime, category, description, loopType);
    }

    private void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            throw new InvalidScheduleTimeException("startTime and endTime must same day");
        }
        if (startTime.isAfter(endTime)) {
            throw new InvalidScheduleTimeException("startTime must before endTime");
        }
    }

    public void update(long memberId, LocalDateTime startTime, LocalDateTime endTime, String category, String description, LoopType loopType) {
        if (this.memberId != memberId) {
            throw new IllegalScheduleAccessException(String.format("해당 스케쥴 (%d)은 수정할 수 없습니다", this.id));
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

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LoopType getLoopType() {
        return loopType;
    }
}
