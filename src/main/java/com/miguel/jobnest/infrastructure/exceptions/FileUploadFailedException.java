package com.miguel.jobnest.infrastructure.exceptions;

public class FileUploadFailedException extends RuntimeException {
    public FileUploadFailedException(final String message, final Throwable cause) {
        super(message);
    }

    public static FileUploadFailedException with(final String message, final Throwable cause) {
        return new FileUploadFailedException(message, cause);
    }
}
