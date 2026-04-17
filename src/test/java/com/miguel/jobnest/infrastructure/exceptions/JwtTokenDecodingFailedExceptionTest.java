package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTokenDecodingFailedExceptionTest {
    @Test
    void shouldReturnJwtTokenDecodingFailedException_whenCallWith() {
        final String message = "Error message";

        final var ex = JwtTokenDecodingFailedException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
