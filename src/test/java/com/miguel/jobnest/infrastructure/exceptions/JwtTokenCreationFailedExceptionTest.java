package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTokenCreationFailedExceptionTest {
    @Test
    void shouldReturnJwtTokenCreationFailedException_whenCallWith() {
        final String message = "Error message";

        final var ex = JwtTokenCreationFailedException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
