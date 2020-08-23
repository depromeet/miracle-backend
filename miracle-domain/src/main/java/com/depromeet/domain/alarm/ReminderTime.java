package com.depromeet.domain.alarm;

import com.deprommet.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ReminderTime {

    private final static int UNIT_OF_TIME = 10;

    @Column(nullable = false)
    private LocalTime reminderTime;

    private ReminderTime(LocalTime reminderTime) {
        validateWakeUpTime(reminderTime);
        this.reminderTime = reminderTime;
    }

    private void validateWakeUpTime(LocalTime reminderTime) {
        if (reminderTime.getMinute() % UNIT_OF_TIME != 0) {
            throw new ValidationException(String.format("기상시간이 10분단위가 아닙니다. (%s)", reminderTime), "기상 시간은 10분단위로 설정할 수 있습니다.");
        }
    }

    public static ReminderTime of(LocalTime reminderTime) {
        return new ReminderTime(reminderTime);
    }

}
