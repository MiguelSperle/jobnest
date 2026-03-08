package com.miguel.jobnest.infrastructure.exceptions;

public class FileUploadFailedException extends RuntimeException {
    public FileUploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FileUploadFailedException with(String message,  Throwable cause) {
        return new FileUploadFailedException(message, cause);
    }
}
