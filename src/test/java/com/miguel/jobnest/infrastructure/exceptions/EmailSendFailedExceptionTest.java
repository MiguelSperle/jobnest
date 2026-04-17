package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailSendFailedExceptionTest {
    @Test
    void shouldReturnEmailSendFailedException_whenCallWith() {
        final String message = "Error message";

        final var ex = EmailSendFailedException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
