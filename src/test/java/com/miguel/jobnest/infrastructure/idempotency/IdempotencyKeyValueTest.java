package com.miguel.jobnest.infrastructure.idempotency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class IdempotencyKeyValueTest {
    @Test
    void shouldReturnIdempotencyKeyValue_whenCallInit() {
        final IdempotencyKeyValue idempotencyKeyValue = IdempotencyKeyValue.init();

        Assertions.assertNotNull(idempotencyKeyValue);
        Assertions.assertEquals(0, idempotencyKeyValue.statusCode());
        Assertions.assertEquals("", idempotencyKeyValue.body());
        Assertions.assertEquals(Map.of(), idempotencyKeyValue.headers());
        Assertions.assertFalse(idempotencyKeyValue.isDone());
    }

    @Test
    void shouldReturnIdempotencyKeyValue_whenCallDone() {
        final int statusCode = 200;
        final String body = "body";

        final IdempotencyKeyValue idempotencyKeyValue = IdempotencyKeyValue.done(statusCode, body, Map.of());

        Assertions.assertNotNull(idempotencyKeyValue);
        Assertions.assertEquals(statusCode, idempotencyKeyValue.statusCode());
        Assertions.assertEquals(body, idempotencyKeyValue.body());
        Assertions.assertEquals(Map.of(), idempotencyKeyValue.headers());
        Assertions.assertTrue(idempotencyKeyValue.isDone());
    }
}
