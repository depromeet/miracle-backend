package com.deprommet.exception;

public class InvalidSessionException extends CustomException {

    public InvalidSessionException(String message, String clientMessage) {
        super(message, clientMessage);
    }

}
