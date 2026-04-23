package com.miguel.jobnest.infrastructure.api;

import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.utils.ApiError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorTest {
    @Test
    void shouldReturnApiErrorWithEmptyErrors_whenCallFrom() {
        final String message = "Message";
        final LocalDateTime timestamp = TimeUtils.now();

        final ApiError apiError = ApiError.from(message, timestamp);

        Assertions.assertNotNull(apiError);
        Assertions.assertEquals(message, apiError.message());
        Assertions.assertEquals(timestamp, apiError.timestamp());
        Assertions.assertTrue(apiError.errors().isEmpty());
    }

    @Test
    void shouldReturnApiErrorWithErrors_whenCallFrom() {
        final String message = "Message";
        final List<String> errors = List.of("Error 1", "Error 2");
        final LocalDateTime timestamp = TimeUtils.now();

        final ApiError apiError = ApiError.from(message, errors, timestamp);


        Assertions.assertNotNull(apiError);
        Assertions.assertEquals(message, apiError.message());
        Assertions.assertEquals(errors, apiError.errors());
        Assertions.assertEquals(timestamp, apiError.timestamp());
    }
}
