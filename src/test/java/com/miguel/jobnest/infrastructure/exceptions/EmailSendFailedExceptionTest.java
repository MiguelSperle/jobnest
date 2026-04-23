package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailSendFailedExceptionTest {
    @Test
    void shouldReturnEmailSendFailedException_whenCallWith() {
        final String message = "Error message";
        final Throwable cause = new RuntimeException();

        final var ex = EmailSendFailedException.with(message, cause);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
        Assertions.assertEquals(cause, ex.getCause());
    }
}
