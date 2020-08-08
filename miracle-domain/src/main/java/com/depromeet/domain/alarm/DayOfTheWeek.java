package com.depromeet.domain.alarm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum DayOfTheWeek {

    MON(DayOfWeek.MONDAY, false),
    TUE(DayOfWeek.TUESDAY, false),
    WED(DayOfWeek.WEDNESDAY, false),
    THU(DayOfWeek.THURSDAY, false),
    FRI(DayOfWeek.FRIDAY, false),
    SAT(DayOfWeek.SATURDAY, true),
    SUN(DayOfWeek.SUNDAY, true);

    private final DayOfWeek dayOfWeek;
    private final boolean isWeekend;

    private static final Map<DayOfWeek, DayOfTheWeek> cachingDayOfTheWeek = new HashMap<>();

    static {
        for (DayOfTheWeek dayOfTheWeek : values()) {
            cachingDayOfTheWeek.put(dayOfTheWeek.getDayOfWeek(), dayOfTheWeek);
        }
    }

    public static DayOfTheWeek of(DayOfWeek dayOfWeek) {
        return cachingDayOfTheWeek.get(dayOfWeek);
    }

}
