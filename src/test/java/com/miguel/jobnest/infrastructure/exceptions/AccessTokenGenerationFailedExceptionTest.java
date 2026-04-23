package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessTokenGenerationFailedExceptionTest {
    @Test
    void shouldReturnAccessTokenGenerationFailedException_whenCallWith() {
        final String message = "Error message";
        final Throwable cause = new RuntimeException();

        final var ex = AccessTokenGenerationFailedException.with(message, cause);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
        Assertions.assertEquals(cause, ex.getCause());
    }
}
