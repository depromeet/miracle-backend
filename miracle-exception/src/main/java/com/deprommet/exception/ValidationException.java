package com.deprommet.exception;

public class ValidationException extends CustomException {

    public ValidationException(String message, String clientMessage) {
        super(message, clientMessage);
    }

}
