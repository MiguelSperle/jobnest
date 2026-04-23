package com.miguel.jobnest.infrastructure.utils;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(String message, int status, List<Error> errors, LocalDateTime timestamp) {
    public record Error(String property, String message) {
    }

    public static ApiError from(final String message, final int status, final List<Error> errors, final LocalDateTime timestamp) {
        return new ApiError(message, status, errors, timestamp);
    }

    public static ApiError from(final String message, final int status, final LocalDateTime timestamp) {
        return new ApiError(message, status, List.of(), timestamp);
    }
}