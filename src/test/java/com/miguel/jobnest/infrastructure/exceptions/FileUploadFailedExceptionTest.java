package com.miguel.jobnest.infrastructure.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileUploadFailedExceptionTest {
    @Test
    void shouldReturnFileUploadFailedException_whenCallWith() {
        final String message = "Error message";

        final var ex = FileUploadFailedException.with(message);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
    }
}
