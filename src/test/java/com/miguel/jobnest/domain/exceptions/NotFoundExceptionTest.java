package com.miguel.jobnest.domain.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {
    @Test
    void shouldReturnNotFoundException_whenCallWith() {
        final String message = "Error message";

        final var ex = NotFoundException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
