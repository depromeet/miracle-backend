package com.depromeet.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeUtilsTest {

    @Test
    void 해당_월의_첫날을_찾는다() {
        // when
        LocalDateTime result = LocalDateTimeUtils.findFirstDayOfTheMonth(2020, 8);

        // then
        assertThat(result).isEqualTo(LocalDateTime.of(2020, 8, 1, 0, 0, 0));
    }

    @Test
    void 해당_월의_마지막_날을_찾는다() {
        // when
        LocalDateTime result = LocalDateTimeUtils.findEndDayOfTheMonth(2020, 8);

        // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2020, 8, 31, 23, 59, 59));
    }

    @Test
    void 해당날의_최소시간을_찾는다() {
        // given
        LocalDate date = LocalDate.of(2020, 8, 26);

        // when
        LocalDateTime result = LocalDateTimeUtils.getMinLocalDateTime(date);

        // then
        assertThat(result).isEqualTo(LocalDateTime.of(2020, 8, 26, 0, 0, 0));
    }

    @Test
    void 해당날의_최대시간을_찾는다() {
        // given
        LocalDate date = LocalDate.of(2020, 8, 26);

        // when
        LocalDateTime result = LocalDateTimeUtils.getMaxLocalDateTime(date);

        // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2020, 8, 26, 23, 59, 59));
    }

}
