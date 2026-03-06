package com.miguel.jobnest.infrastructure.abstractions.resolvers.eventrouting;

import com.miguel.jobnest.infrastructure.resolvers.eventrouting.EventRouting;

public interface EventRoutingResolver {
    EventRouting resolve(String eventType);
}
