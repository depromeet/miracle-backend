package com.depromeet.config.advice;

import com.depromeet.ApiResponse;
import com.deprommet.exception.ConflictException;
import com.deprommet.exception.InvalidSessionException;
import com.deprommet.exception.NotFoundException;
import com.deprommet.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.depromeet.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> notFoundException(NotFoundException e) {
        return new ApiResponse<>("NOT_FOUND_EXCEPTION", e.getMessage(), null);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> conflictException(ConflictException e) {
        return new ApiResponse<>("CONFLICT_EXCEPTION", e.getMessage(), null);
    }

    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Object> invalidSessionException(InvalidSessionException e) {
        return new ApiResponse<>("INVALID_SESSION_EXCEPTION", e.getMessage(), null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> validationException(ValidationException e) {
        return new ApiResponse<>("VALIDATION_EXCEPTION", e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ApiResponse<>("BAD_REQUEST_EXCEPTION", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }

}
