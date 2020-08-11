package com.depromeet.config.advice;

import com.depromeet.ApiResponse;
import com.deprommet.exception.ConflictException;
import com.deprommet.exception.InvalidSessionException;
import com.deprommet.exception.NotFoundException;
import com.deprommet.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.depromeet.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> notFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>("NOT_FOUND_EXCEPTION", e.getClientMessage(), null);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> conflictException(ConflictException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>("CONFLICT_EXCEPTION", e.getClientMessage(), null);
    }

    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Object> invalidSessionException(InvalidSessionException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>("INVALID_SESSION_EXCEPTION", e.getClientMessage(), null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> validationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>("VALIDATION_EXCEPTION", e.getClientMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse<>("BAD_REQUEST_EXCEPTION", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }

}
