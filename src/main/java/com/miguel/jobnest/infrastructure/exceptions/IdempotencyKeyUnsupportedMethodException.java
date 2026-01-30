package com.miguel.jobnest.infrastructure.exceptions;

public class IdempotencyKeyUnsupportedMethodException extends RuntimeException {
    public IdempotencyKeyUnsupportedMethodException(String message) {
        super(message);
    }

    public static IdempotencyKeyUnsupportedMethodException with(String message) {
        return new IdempotencyKeyUnsupportedMethodException(message);
    }
}
