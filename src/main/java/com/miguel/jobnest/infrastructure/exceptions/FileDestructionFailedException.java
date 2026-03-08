package com.miguel.jobnest.infrastructure.exceptions;

public class FileDestructionFailedException extends RuntimeException {
    public FileDestructionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FileDestructionFailedException with(String message, Throwable cause) {
        return new FileDestructionFailedException(message, cause);
    }
}
