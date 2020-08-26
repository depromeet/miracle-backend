package com.depromeet.utils;

import com.deprommet.exception.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtils {

    public static LocalDateTime findFirstDayOfTheMonth(int year, int month) {
        try {
            LocalDate firstDay = LocalDate.of(year, month, 1);
            return LocalDateTime.of(firstDay, LocalTime.MIN);
        } catch (DateTimeException e) {
            throw new ValidationException(String.format("잘못된 연도(%s) 월(%s)이 입력되었습니다", year, month), "잘못된 날짜가 입력되었습니다.");
        }
    }

    public static LocalDateTime findEndDayOfTheMonth(int year, int month) {
        try {
            LocalDate firstDay = YearMonth.of(year, month).atEndOfMonth();
            return LocalDateTime.of(firstDay, LocalTime.MAX);
        } catch (DateTimeException e) {
            throw new ValidationException(String.format("잘못된 연도(%s) 월(%s)이 입력되었습니다", year, month), "잘못된 날짜가 입력되었습니다.");
        }
    }

    public static LocalDateTime getMinLocalDateTime(LocalDate targetDate) {
        return LocalDateTime.of(targetDate, LocalTime.MIN);
    }

    public static LocalDateTime getMaxLocalDateTime(LocalDate targetDate) {
        return LocalDateTime.of(targetDate, LocalTime.MAX);
    }

}
