package com.depromeet.domain.common;

import com.deprommet.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateTimeIntervalTest {

    @Test
    void 시작날짜가_종료날짜보다_이후이면_에러발생한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 26, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 25, 0, 0, 0);

        // when & then
        assertThatThrownBy(() -> {
            DateTimeInterval.of(startDateTime, endDateTime);
        }).isInstanceOf(ValidationException.class);
    }

    @Test
    void 시작날짜가_종료날짜보다_이전이면_정상작동한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 25, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 26, 0, 0, 0);

        // when
        DateTimeInterval dateTimeInterval = DateTimeInterval.of(startDateTime, endDateTime);

        // then
        assertThat(dateTimeInterval.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(dateTimeInterval.getEndDateTime()).isEqualTo(endDateTime);
    }

}
