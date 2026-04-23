package com.miguel.jobnest.infrastructure.exceptions;

public class AccessTokenGenerationFailedException extends RuntimeException {
    public AccessTokenGenerationFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static AccessTokenGenerationFailedException with(final String message, final Throwable cause) {
        return new AccessTokenGenerationFailedException(message, cause);
    }
}
