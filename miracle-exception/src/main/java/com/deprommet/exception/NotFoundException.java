package com.deprommet.exception;

public class NotFoundException extends CustomException {

    public NotFoundException(String message, String clientMessage) {
        super(message, clientMessage);
    }

}
