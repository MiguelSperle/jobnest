package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdempotencyKeyProcessingExceptionTest {
    @Test
    void shouldReturnIdempotencyKeyProcessingException_whenCallWith() {
        final String message = "Error message";

        final var ex = IdempotencyKeyProcessingException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
