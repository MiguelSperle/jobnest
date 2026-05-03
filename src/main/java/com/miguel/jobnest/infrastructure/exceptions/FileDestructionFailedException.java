package com.miguel.jobnest.infrastructure.exceptions;

public class FileDestructionFailedException extends RuntimeException {
    public FileDestructionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static FileDestructionFailedException with(final String message, final Throwable cause) {
        return new FileDestructionFailedException(message, cause);
    }
}
