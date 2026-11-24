package com.utp.platform.learning.domain.events;

import java.time.Instant;
import com.utp.platform.shared.domain.model.events.DomainEvent;

public record StudentCreatedEvent(
        Long studentId,
        String studentRecordId,
        String email,
        Instant occurredOn
) implements DomainEvent {
}
