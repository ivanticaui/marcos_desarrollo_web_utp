package com.utp.platform.learning.domain.events;

import java.time.Instant;

import com.utp.platform.shared.domain.model.events.DomainEvent;

public record EnrollmentConfirmedEvent(Long enrollmentId,
                                       Long studentId, Long courseId, Instant occurredAt)
        implements DomainEvent {
}
