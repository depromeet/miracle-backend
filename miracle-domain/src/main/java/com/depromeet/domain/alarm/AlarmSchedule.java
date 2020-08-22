package com.depromeet.domain.alarm;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AlarmSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType type;

    private String description;

    //Todo 스케쥴 지워지면이 스케줄이 갖고있는 알람도 지워지게 하는
    @OneToMany(mappedBy = "alarmSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @Builder
    public AlarmSchedule(Long memberId, AlarmType type, String description) {
        this.memberId = memberId;
        this.type = type;
        this.description = description;
    }

    public static AlarmSchedule newInstance(Long memberId, AlarmType type, String description) {
        return AlarmSchedule.builder()
            .memberId(memberId)
            .type(type)
            .description(description)
            .build();
    }

    public static AlarmSchedule defaultWakeUpAlarmScheduleInstance(Long memberId, LocalTime wakeUpTime) {
        AlarmSchedule alarmSchedule = new AlarmSchedule(memberId, AlarmType.WAKE_UP, "기상 알람");
        alarmSchedule.addAlarms(Alarm.defaultWakeUpAlarmInstance(wakeUpTime));
        return alarmSchedule;
    }

    public void updateAlarmScheduleInfo(AlarmType type, String description, List<Alarm> alarms) {
        this.type = type;
        this.description = description;
        updateAlarms(alarms);
    }

    private void updateAlarms(List<Alarm> alarms) {
        if (hasSameAlarms(alarms)) {
            return;
        }
        removeAlarms();
        addAlarms(alarms);
    }

    private boolean hasSameAlarms(List<Alarm> others) {
        if (this.alarms.size() != others.size()) {
            return false;
        }
        return this.alarms.containsAll(others);
    }

    private void removeAlarms() {
        this.alarms.clear();
    }

    public void addAlarms(List<Alarm> alarmList) {
        for (Alarm alarm : alarmList) {
            addAlarm(alarm);
        }
    }

    private void addAlarm(Alarm alarm) {
        alarm.setAlarmSchedule(this);
        this.alarms.add(alarm);
    }

}
