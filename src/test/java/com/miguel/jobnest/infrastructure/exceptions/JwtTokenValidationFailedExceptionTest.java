package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTokenValidationFailedExceptionTest {
    @Test
    void shouldReturnJwtTokenValidationFailedException_whenCallWith() {
        final String message = "Error message";

        final JwtTokenValidationFailedException ex = JwtTokenValidationFailedException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
