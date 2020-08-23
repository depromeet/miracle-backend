package com.depromeet.domain.alarm;

import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReminderTimeTest {

    @Test
    void 십분단위인지_검증한다_10분단위_일경우_정상작동한다() {
        // given
        LocalTime localTime = LocalTime.of(8, 10);

        // when
        ReminderTime reminderTime = ReminderTime.of(localTime);

        // then
        assertThat(reminderTime.getReminderTime()).isEqualTo(localTime);
    }

    @Test
    void 십분단위인지_검증한다_0분일_경우_정상_작동한다() {
        // given
        LocalTime localTime = LocalTime.of(0, 0);

        // when
        ReminderTime reminderTime = ReminderTime.of(localTime);

        // then
        assertThat(reminderTime.getReminderTime()).isEqualTo(localTime);
    }

    @Test
    void 십분단위인지_검증한다_아닐경우_예외발생한다() {
        // given
        LocalTime localTime = LocalTime.of(8, 15);

        // when
        assertThatThrownBy(() -> {
            ReminderTime.of(localTime);
        }).isInstanceOf(ValidationException.class);
    }

}
