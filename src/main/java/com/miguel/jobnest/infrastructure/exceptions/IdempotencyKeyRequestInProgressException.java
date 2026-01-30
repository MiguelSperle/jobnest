package com.miguel.jobnest.infrastructure.exceptions;

public class IdempotencyKeyRequestInProgressException extends RuntimeException {
    public IdempotencyKeyRequestInProgressException(String message) {
        super(message);
    }

    public static IdempotencyKeyRequestInProgressException with(String message) {
        return new IdempotencyKeyRequestInProgressException(message);
    }
}
