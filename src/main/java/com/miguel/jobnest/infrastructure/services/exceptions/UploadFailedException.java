package com.miguel.jobnest.infrastructure.services.exceptions;

public class UploadFailedException extends RuntimeException {
    public UploadFailedException(String message) {
        super(message);
    }

    public static UploadFailedException with(String message) {
        return new UploadFailedException(message);
    }
}
