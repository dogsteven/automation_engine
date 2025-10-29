package com.foxsteven.automation_engine.common.abstractions;

import jakarta.persistence.Transient;

import java.util.ArrayList;

public abstract class DomainEventSource {
    @Transient
    private final ArrayList<Object> domainEvents = new ArrayList<>();

    public Iterable<Object> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    protected void raiseDomainEvent(Object domainEvent) {
        domainEvents.add(domainEvent);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
