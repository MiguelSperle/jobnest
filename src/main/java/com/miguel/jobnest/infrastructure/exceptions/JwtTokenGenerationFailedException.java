package com.miguel.jobnest.infrastructure.exceptions;

public class JwtTokenGenerationFailedException extends RuntimeException {
    public JwtTokenGenerationFailedException(String message) {
        super(message);
    }

    public static JwtTokenGenerationFailedException with(String message) {
        return new JwtTokenGenerationFailedException(message);
    }
}
