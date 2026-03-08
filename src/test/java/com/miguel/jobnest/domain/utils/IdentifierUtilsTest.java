package com.miguel.jobnest.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdentifierUtilsTest {
    @Test
    void shouldReturnId_whenCallGenerateNewId() {
        final String id = IdentifierUtils.generateNewId();

        Assertions.assertNotNull(id);
        Assertions.assertTrue(id.matches("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}"));
    }
}
