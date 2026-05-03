package com.miguel.jobnest.infrastructure.utils;

import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorTest {
    @Test
    void shouldReturnApiErrorWithEmptyErrors_whenCallFrom() {
        final String message = "Message";
        final LocalDateTime timestamp = TimeUtils.now();

        final ApiError apiError = ApiError.from(message, 200, timestamp);

        Assertions.assertNotNull(apiError);
        Assertions.assertEquals(message, apiError.message());
        Assertions.assertEquals(timestamp, apiError.timestamp());
        Assertions.assertTrue(apiError.errors().isEmpty());
    }

    @Test
    void shouldReturnApiErrorWithErrors_whenCallFrom() {
        final String message = "Message";
        final List<ApiError.Error> errors = List.of(
                new ApiError.Error("field1", "Error 1"),
                new ApiError.Error("field2", "Error 2")
        );
        final LocalDateTime timestamp = TimeUtils.now();

        final ApiError apiError = ApiError.from(message, 400, errors, timestamp);


        Assertions.assertNotNull(apiError);
        Assertions.assertEquals(message, apiError.message());
        Assertions.assertEquals(errors, apiError.errors());
        Assertions.assertEquals(timestamp, apiError.timestamp());
    }
}
