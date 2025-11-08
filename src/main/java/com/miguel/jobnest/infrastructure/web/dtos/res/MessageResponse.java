package com.miguel.jobnest.infrastructure.web.dtos.res;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}
