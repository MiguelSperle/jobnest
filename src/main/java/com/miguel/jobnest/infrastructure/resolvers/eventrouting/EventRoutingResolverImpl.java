package com.miguel.jobnest.infrastructure.resolvers.eventrouting;

import com.miguel.jobnest.infrastructure.abstractions.resolvers.eventrouting.EventRoutingResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventRoutingResolverImpl implements EventRoutingResolver {
    private final Map<String, EventRouting> eventRoutingMap = Map.of(
            "UserCodeCreated", new EventRouting("user.code.events", "user.code.created"),
            "SubscriptionCreated", new EventRouting("subscription.events", "subscription.created")
    );

    @Override
    public EventRouting resolve(final String eventType) {
        final EventRouting eventRouting = this.eventRoutingMap.get(eventType);

        if (eventRouting == null) {
            throw new IllegalArgumentException("No event routing configured for eventType: " + eventType);
        }

        return eventRouting;
    }
}
