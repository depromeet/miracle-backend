package com.depromeet.service.record.dto.request;

import com.depromeet.utils.LocalDateTimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DayRecordsRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private DayRecordsRequest(LocalDate date) {
        this.date = date;
    }

    public static DayRecordsRequest testInstance(LocalDate date) {
        return new DayRecordsRequest(date);
    }

    public LocalDateTime convertMinLocalDateTime() {
        return LocalDateTimeUtils.getMinLocalDateTime(date);
    }

    public LocalDateTime convertMaxLocalDateTime() {
        return LocalDateTimeUtils.getMaxLocalDateTime(date);
    }

}
