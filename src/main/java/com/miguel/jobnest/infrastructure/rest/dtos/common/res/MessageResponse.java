package com.miguel.jobnest.infrastructure.rest.dtos.common.res;

public record MessageResponse(
        String message
) {
    public static MessageResponse from(String message) {
        return new MessageResponse(message);
    }
}
