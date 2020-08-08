package com.depromeet.domain.alarm;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_schedule_id")
    private AlarmSchedule alarmSchedule;

    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;

    private LocalTime reminderTime;

    @Builder
    public Alarm(AlarmSchedule alarmSchedule, DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        this.alarmSchedule = alarmSchedule;
        this.dayOfTheWeek = dayOfTheWeek;
        this.reminderTime = reminderTime;
    }

    public static Alarm newInstance(DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        return Alarm.builder()
            .dayOfTheWeek(dayOfTheWeek)
            .reminderTime(reminderTime)
            .build();
    }

    void setAlarmSchedule(AlarmSchedule alarmSchedule) {
        this.alarmSchedule = alarmSchedule;
    }

}
