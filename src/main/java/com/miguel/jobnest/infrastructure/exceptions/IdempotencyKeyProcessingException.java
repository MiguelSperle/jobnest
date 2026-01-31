package com.miguel.jobnest.infrastructure.exceptions;

public class IdempotencyKeyProcessingException extends RuntimeException {
    public IdempotencyKeyProcessingException(String message) {
        super(message);
    }

    public static IdempotencyKeyProcessingException with(String message) {
        return new IdempotencyKeyProcessingException(message);
    }
}
