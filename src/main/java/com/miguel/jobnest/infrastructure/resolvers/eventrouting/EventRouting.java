package com.miguel.jobnest.infrastructure.resolvers.eventrouting;

public record EventRouting(
        String exchange,
        String routingKey
) {
}
