package com.miguel.jobnest.domain.events;

public record UserCodeCreatedEvent(
        String id
) {
    public static UserCodeCreatedEvent from(String id) {
        return new UserCodeCreatedEvent(id);
    }
}
