package com.miguel.jobnest.infrastructure.utils;

import java.util.List;

public record ApiError(
        List<String> errors,
        String errorType
) {
    public static ApiError from(final List<String> errors, final String errorType) {
        return new ApiError(errors, errorType);
    }
}
