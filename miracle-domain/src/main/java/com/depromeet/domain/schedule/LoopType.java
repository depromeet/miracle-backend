package com.depromeet.domain.schedule;

import java.util.Arrays;

public enum LoopType {
    NONE("NONE"),
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH");

    private final String text;

    LoopType(String text) {
        this.text = text;
    }

    public static LoopType of(String text) {
        return Arrays.stream(values())
            .filter(dayOfWeek -> dayOfWeek.text.equals(text))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Parameter is invalid. %s", text)));
    }

    public String getText() {
        return text;
    }
}
