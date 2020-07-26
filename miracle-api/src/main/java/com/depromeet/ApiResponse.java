package com.depromeet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    public static ApiResponse<String> OK = new ApiResponse<>("", "", "OK");

    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>("", "", data);
    }

}
