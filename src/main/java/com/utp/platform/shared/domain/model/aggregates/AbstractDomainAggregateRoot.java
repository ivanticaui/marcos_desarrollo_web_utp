package com.utp.platform.shared.domain.model.aggregates;

import com.utp.platform.shared.domain.model.events.DomainEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Framework-independent domain event tracking for aggregate roots. */
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>> {
    private final List<DomainEvent> pendingEvents = new ArrayList<>();

    protected final void registerDomainEvent(DomainEvent event) {
        pendingEvents.add(Objects.requireNonNull(event, "Domain event cannot be null"));
    }

    /** Returns and clears pending events; call only after successful persistence. */
    public final List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> snapshot = List.copyOf(pendingEvents);
        pendingEvents.clear();
        return snapshot;
    }
}
