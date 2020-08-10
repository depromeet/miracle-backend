package com.depromeet.domain.schedule;

public class IllegalScheduleAccessException extends RuntimeException {
    public IllegalScheduleAccessException(String message) {
        super(message);
    }
}
