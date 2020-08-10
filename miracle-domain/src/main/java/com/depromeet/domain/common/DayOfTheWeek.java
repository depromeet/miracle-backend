package com.depromeet.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static final List<DayOfTheWeek> everyDay = new ArrayList<>();
    private static final Map<DayOfWeek, DayOfTheWeek> cachingDayOfTheWeek = new HashMap<>();

    static {
        everyDay.addAll(Arrays.stream(values())
            .collect(Collectors.toList()));

        for (DayOfTheWeek dayOfTheWeek : values()) {
            cachingDayOfTheWeek.put(dayOfTheWeek.getDayOfWeek(), dayOfTheWeek);
        }
    }

    public static DayOfTheWeek of(DayOfWeek dayOfWeek) {
        return cachingDayOfTheWeek.get(dayOfWeek);
    }

}
