package com.depromeet.domain.common;

import com.deprommet.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateTimeInterval {

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private DateTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        validateDateTime(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new ValidationException(String.format("시작 날짜 (%s) 가 종료 날짜 (%s)보다 이후일 수 없습니다", startDateTime, endDateTime), "시작 날짜가 종료 날짜 이후일 수 없습니다");
        }
    }

    public static DateTimeInterval of(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new DateTimeInterval(startDateTime, endDateTime);
    }

}
