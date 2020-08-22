package com.depromeet.domain.schedule.record;


import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long memberId;

    private long scheduleId;

    private LocalTime time;

    private int year;

    private int month;

    private int day;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private String content;

    public Record() {
        //needed by hibernate
    }

    public Record(long memberId, long scheduleId, LocalDateTime time, String content) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.year = time.getYear();
        this.month = time.getMonthValue();
        this.day = time.getDayOfMonth();
        this.dayOfWeek = time.getDayOfWeek();
        this.time = time.toLocalTime();
        this.content = content;
    }

    public static Record of(long memberId, long scheduleId, LocalDateTime time, String content) {
        return new Record(memberId, scheduleId, time, content);
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getScheduleId() {
        return scheduleId;
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

    public LocalTime getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
