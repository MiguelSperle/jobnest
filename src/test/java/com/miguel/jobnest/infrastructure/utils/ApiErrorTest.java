package com.miguel.jobnest.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class ApiErrorTest {
    @Test
    void shouldReturnApiError_whenCallFrom() {
        final List<String> errors = Collections.singletonList("Error message");
        final String errorType = "Simulated error";

        final ApiError apiError = ApiError.from(errors, errorType);

        Assertions.assertNotNull(apiError);
        Assertions.assertEquals(errors, apiError.errors());
        Assertions.assertEquals(errorType, apiError.errorType());
    }
}
