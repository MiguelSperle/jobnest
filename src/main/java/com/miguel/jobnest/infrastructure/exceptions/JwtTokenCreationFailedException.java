package com.miguel.jobnest.infrastructure.exceptions;

public class JwtTokenCreationFailedException extends RuntimeException {
    public JwtTokenCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static JwtTokenCreationFailedException with(String message, Throwable cause) {
        return new JwtTokenCreationFailedException(message, cause);
    }
}
