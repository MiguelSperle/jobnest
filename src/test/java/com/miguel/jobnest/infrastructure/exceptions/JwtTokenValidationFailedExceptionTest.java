package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTokenValidationFailedExceptionTest {
    @Test
    void shouldReturnJwtTokenValidationFailedException_whenCallWith() {
        final String message = "Error message";
        final Throwable cause = new RuntimeException();

        final var ex = JwtTokenValidationFailedException.with(message, cause);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
        Assertions.assertEquals(cause, ex.getCause());
    }
}
