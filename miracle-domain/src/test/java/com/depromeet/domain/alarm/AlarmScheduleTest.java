package com.depromeet.domain.alarm;

import com.depromeet.domain.common.DayOfTheWeek;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlarmScheduleTest {

    @Test
    void 두_알람이_같은경우_hasSameAlarms_returnTrue() {
        // given
        AlarmSchedule alarmSchedule1 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description1");
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule1.addAlarms(Arrays.asList(alarm1, alarm2));

        AlarmSchedule alarmSchedule2 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description2");
        Alarm alarm3 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm4 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule2.addAlarms(Arrays.asList(alarm3, alarm4));

        // when & then
        assertTrue(alarmSchedule1.hasSameAlarms(alarmSchedule2));
    }

    @Test
    void 알람_두개다_빈경우_hasSameAlarms_returnTrue() {
        // given
        AlarmSchedule alarmSchedule1 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description1");
        alarmSchedule1.addAlarms(Collections.emptyList());

        AlarmSchedule alarmSchedule2 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description2");
        alarmSchedule2.addAlarms(Collections.emptyList());

        // when & then
        assertTrue(alarmSchedule1.hasSameAlarms(alarmSchedule2));
    }

    @Test
    void 알람의_수가_다른경우_hasSameAlarms_returnFalse() {
        // given
        AlarmSchedule alarmSchedule1 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description1");
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule1.addAlarms(Arrays.asList(alarm1, alarm2));

        AlarmSchedule alarmSchedule2 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description2");
        Alarm alarm3 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        alarmSchedule2.addAlarms(Collections.singletonList(alarm3));

        // when & then
        assertFalse(alarmSchedule1.hasSameAlarms(alarmSchedule2));
    }

    @Test
    void 알람의_시간이_다른경우_hasSameAlarms_returnFalse() {
        // given
        AlarmSchedule alarmSchedule1 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description1");
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule1.addAlarms(Arrays.asList(alarm1, alarm2));

        AlarmSchedule alarmSchedule2 = AlarmScheduleCreator.createAlarmSchedule(1L, AlarmType.WAKE_UP, "description2");
        Alarm alarm3 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm4 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(10, 0));
        alarmSchedule2.addAlarms(Arrays.asList(alarm3, alarm4));

        // when & then
        assertFalse(alarmSchedule1.hasSameAlarms(alarmSchedule2));
    }

}
