package com.miguel.jobnest.infrastructure.exceptions;

public class FileDestructionFailedException extends RuntimeException {
    public FileDestructionFailedException(String message) {
        super(message);
    }

    public static FileDestructionFailedException with(String message) {
        return new FileDestructionFailedException(message);
    }
}
