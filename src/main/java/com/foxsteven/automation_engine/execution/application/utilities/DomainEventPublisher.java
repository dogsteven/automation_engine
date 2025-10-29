package com.foxsteven.automation_engine.execution.application.utilities;

import com.foxsteven.automation_engine.common.abstractions.DomainEventSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishDomainEvents(DomainEventSource source) {
        final var domainEvents = source.getDomainEvents();
        source.clearDomainEvents();

        for (final var domainEvent: domainEvents) {
            eventPublisher.publishEvent(domainEvent);
        }
    }
}
