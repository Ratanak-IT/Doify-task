package com.taskflow.dto.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiSuccessResponse<T>(
        int code,
        String status,
        String message,
        T data,
        Instant timestamp
) {
    public static <T> ApiSuccessResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiSuccessResponse<>(
                status.value(),
                status.getReasonPhrase(),
                message,
                data,
                Instant.now()
        );
    }

    public static <T> ApiSuccessResponse<T> ok(String message, T data) {
        return of(HttpStatus.OK, message, data);
    }

    public static ApiSuccessResponse<Void> ok(String message) {
        return of(HttpStatus.OK, message, null);
    }

    public static <T> ApiSuccessResponse<T> created(String message, T data) {
        return of(HttpStatus.CREATED, message, data);
    }
}