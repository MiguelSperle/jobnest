package com.miguel.jobnest.infrastructure.idempotency;

import java.io.Serializable;
import java.util.Map;

public record IdempotencyValue(int statusCode, String body, Map<String, String> headers, boolean isDone) implements Serializable {
    public static IdempotencyValue init() {
        return new IdempotencyValue(0, "", Map.of(), false);
    }

    public static IdempotencyValue done(int statusCode, String body, Map<String, String> headers) {
        return new IdempotencyValue(statusCode, body, headers, true);
    }
}
