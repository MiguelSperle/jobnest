package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdempotencyKeyAlreadyExistsExceptionTest {
    @Test
    void shouldReturnIdempotencyKeyAlreadyExistsException_whenCallWith() {
        final String message = "Error message";

        final var ex = IdempotencyKeyAlreadyExistsException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
