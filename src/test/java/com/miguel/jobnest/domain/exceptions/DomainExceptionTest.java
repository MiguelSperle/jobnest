package com.miguel.jobnest.domain.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomainExceptionTest {
    @Test
    void shouldReturnDomainException_whenCallWith() {
        final String message = "Common error";
        final int statusCode = 400;

        final DomainException ex = DomainException.with(message, statusCode);

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(message, ex.getMessage());
        Assertions.assertEquals(statusCode, ex.getStatusCode());
    }
}
