package com.miguel.jobnest.domain;

import com.miguel.jobnest.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity {
    private final String id;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected Entity(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }

    public void registerEvent(final DomainEvent domainEvent) {
        if (domainEvent == null) {
            return;
        }

        this.domainEvents.add(domainEvent);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Entity entity = (Entity) object;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
