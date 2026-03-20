package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TooManyRequestsExceptionTest {
    @Test
    void shouldReturnTooManyRequestsException_whenCallWith() {
        final String message = "Error message";

        final var ex = TooManyRequestsException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
