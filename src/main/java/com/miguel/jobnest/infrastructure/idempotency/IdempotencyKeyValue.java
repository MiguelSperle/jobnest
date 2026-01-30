package com.miguel.jobnest.infrastructure.idempotency;

import java.io.Serializable;
import java.util.Map;

public record IdempotencyKeyValue(int statusCode, String body, Map<String, String> headers) implements Serializable {
    public static IdempotencyKeyValue init() {
        return new IdempotencyKeyValue(0, "", Map.of());
    }

    public static IdempotencyKeyValue done(int statusCode, String body, Map<String, String> headers) {
        return new IdempotencyKeyValue(statusCode, body, headers);
    }
}
