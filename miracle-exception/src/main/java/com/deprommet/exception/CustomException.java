package com.deprommet.exception;

import lombok.Getter;

@Getter
abstract class CustomException extends RuntimeException {

    private String clientMessage;

    CustomException(String message, String clientMessage) {
        super(message);
        this.clientMessage = clientMessage;
    }

}
