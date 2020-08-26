package com.depromeet.service.record.dto.request;

import com.depromeet.utils.LocalDateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MonthRecordsRequest {

    private int year;

    private int month;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public MonthRecordsRequest(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public LocalDateTime convertFirstDayOfTheMonth() {
        return LocalDateTimeUtils.findFirstDayOfTheMonth(year, month);
    }

    public LocalDateTime convertEndDayOfTheMonth() {
        return LocalDateTimeUtils.findEndDayOfTheMonth(year, month);
    }

}
