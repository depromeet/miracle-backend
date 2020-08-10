package com.depromeet.event.alarm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NewMemberRegisteredEvent {

    private final Long memberId;

    private final LocalTime wakeUpTime;

    public static NewMemberRegisteredEvent of(Long memberId, LocalTime wakeUpTime) {
        return new NewMemberRegisteredEvent(memberId, wakeUpTime);
    }

}
