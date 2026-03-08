package com.miguel.jobnest.infrastructure.exceptions;

public class JwtTokenValidationFailedException extends RuntimeException {
    public JwtTokenValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static JwtTokenValidationFailedException with(String message, Throwable cause) {
        return new JwtTokenValidationFailedException(message, cause);
    }
}
