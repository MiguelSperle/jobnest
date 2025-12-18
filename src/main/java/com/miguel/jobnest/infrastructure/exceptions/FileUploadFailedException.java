package com.miguel.jobnest.infrastructure.exceptions;

public class FileUploadFailedException extends RuntimeException {
    public FileUploadFailedException(String message) {
        super(message);
    }

    public static FileUploadFailedException with(String message) {
        return new FileUploadFailedException(message);
    }
}
