package com.miguel.jobnest.infrastructure.exceptions;

public class JwtTokenDecodingFailedException extends RuntimeException {
    public JwtTokenDecodingFailedException(String message) {
        super(message);
    }

    public static JwtTokenDecodingFailedException with(String message) {
        return new JwtTokenDecodingFailedException(message);
    }
}
