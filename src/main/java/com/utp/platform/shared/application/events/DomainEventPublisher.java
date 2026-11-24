package com.utp.platform.shared.application.events;

import com.utp.platform.shared.domain.model.events.DomainEvent;

/**
 * Application port, implemented by an infrastructure adapter.
 */
public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
