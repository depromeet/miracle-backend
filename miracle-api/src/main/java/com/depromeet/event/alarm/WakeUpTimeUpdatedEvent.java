package com.depromeet.event.alarm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WakeUpTimeUpdatedEvent {

    private final Long memberId;

    private final LocalTime wakeUpTime;

    public static WakeUpTimeUpdatedEvent of(Long memberId, LocalTime wakeUpTime) {
        return new WakeUpTimeUpdatedEvent(memberId, wakeUpTime);
    }

}
