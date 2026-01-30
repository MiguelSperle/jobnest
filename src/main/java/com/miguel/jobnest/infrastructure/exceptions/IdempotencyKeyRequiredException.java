package com.miguel.jobnest.infrastructure.exceptions;

public class IdempotencyKeyRequiredException extends RuntimeException {
    public IdempotencyKeyRequiredException(String message) {
        super(message);
    }

    public static IdempotencyKeyRequiredException with(String message) {
        return new IdempotencyKeyRequiredException(message);
    }
}
