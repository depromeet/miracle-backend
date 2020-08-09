package com.depromeet.domain.alarm;

import com.depromeet.domain.common.DayOfTheWeek;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AlarmTest {

    @Test
    void 동등성_테스트_같은_요일_같은_시간인_경우() {
        // given
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));

        // when & then
        assertEquals(alarm1, alarm2);
    }

    @Test
    void 동등성_테스트_다른_요일_같은_시간인_경우() {
        // given
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(8, 0));

        // when & then
        assertNotEquals(alarm1, alarm2);
    }

    @Test
    void 동등성_테스트_같은_요일_다른_시간인경우() {
        // given
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(9, 0));

        // when & then
        assertNotEquals(alarm1, alarm2);
    }

}
