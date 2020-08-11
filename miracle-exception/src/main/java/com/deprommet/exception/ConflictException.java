package com.deprommet.exception;

public class ConflictException extends CustomException {

    public ConflictException(String message, String clientMessage) {
        super(message, clientMessage);
    }

}
