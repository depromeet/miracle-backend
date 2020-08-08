package com.depromeet.domain.alarm;

import com.depromeet.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AlarmSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private AlarmType type;

    private String description;

    @OneToMany(mappedBy = "alarmSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @Builder()
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

    public void updateAlarmScheduleInfo(AlarmType type, String description) {
        this.type = type;
        this.description = description;
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

    public void updateAlarms(List<Alarm> alarms) {
        removeAlarms();
        addAlarms(alarms);
    }

    private void removeAlarms() {
        this.alarms.clear();
    }

    public void validateMemberHasOwner(Long memberId) {
        if (!isOwner(memberId)) {
            throw new IllegalArgumentException(String.format("AlarmSchedule (%s)은 멤버 (%s)의 알림 스케쥴이 아닙니다", id, memberId));
        }
    }

    private boolean isOwner(Long memberId) {
        return this.memberId.equals(memberId);
    }

    public boolean hasSameAlarms(AlarmSchedule other) {
        if (this.alarms.size() != other.alarms.size()) {
            return false;
        }
        return this.alarms.containsAll(other.alarms);
    }

}
