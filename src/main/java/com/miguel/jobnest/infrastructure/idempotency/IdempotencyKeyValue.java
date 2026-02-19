package com.miguel.jobnest.infrastructure.idempotency;

import java.io.Serializable;
import java.util.Map;

public record IdempotencyKeyValue(int statusCode, String body, Map<String, String> headers, boolean isDone) implements Serializable {
    public static IdempotencyKeyValue init() {
        return new IdempotencyKeyValue(0, "", Map.of(), false);
    }

    public static IdempotencyKeyValue done(int statusCode, String body, Map<String, String> headers) {
        return new IdempotencyKeyValue(statusCode, body, headers, true);
    }
}
