package com.miguel.jobnest.infrastructure.exceptions;

public class IdempotencyKeyAlreadyExistsException extends RuntimeException {
    public IdempotencyKeyAlreadyExistsException(String message) {
        super(message);
    }

    public static IdempotencyKeyAlreadyExistsException with(String message) {
        return new IdempotencyKeyAlreadyExistsException(message);
    }
}
