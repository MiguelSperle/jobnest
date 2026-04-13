package com.miguel.jobnest.infrastructure.exceptions;

public class JwtTokenCreationFailedException extends RuntimeException {
    public JwtTokenCreationFailedException(String message) {
        super(message);
    }

    public static JwtTokenCreationFailedException with(String message) {
        return new JwtTokenCreationFailedException(message);
    }
}
