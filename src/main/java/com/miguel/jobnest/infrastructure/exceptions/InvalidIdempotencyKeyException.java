package com.miguel.jobnest.infrastructure.exceptions;

public class InvalidIdempotencyKeyException extends RuntimeException {
    public InvalidIdempotencyKeyException(String message) {
        super(message);
    }

    public static InvalidIdempotencyKeyException with(String message) {
        return new InvalidIdempotencyKeyException(message);
    }
}
