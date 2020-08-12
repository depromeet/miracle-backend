package com.depromeet.domain.schedule;

import io.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;

@ApiModel
public enum LoopType {
    NONE("NONE"),
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH");

    private final String text;

    LoopType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private final static Map<String, LoopType> cachingLoopType = new HashMap<>();

    static {
        for (LoopType loopType : values()) {
            cachingLoopType.put(loopType.getText(), loopType);
        }
    }

    public static LoopType of(String text) {
        if (!cachingLoopType.containsKey(text)) {
            throw new IllegalArgumentException(String.format("Parameter is invalid. %s", text));
        }
        return cachingLoopType.get(text);
    }
}
