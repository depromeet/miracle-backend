package com.depromeet.domain.channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ChannelGoalPeriod {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private ChannelGoalPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static ChannelGoalPeriod of(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        validatePeriod(startDateTime, endDateTime);
        return new ChannelGoalPeriod(startDateTime, endDateTime);
    }

    private static void validatePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            return;
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException(String.format("시작 날짜 (%s) 가 종료 날짜 (%s) 이후일 수 없습니다", startDateTime, endDateTime));
        }
    }

}
