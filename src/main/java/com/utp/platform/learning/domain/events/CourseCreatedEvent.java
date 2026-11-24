package com.utp.platform.learning.domain.events;
import java.time.Instant;
import com.utp.platform.shared.domain.model.events.DomainEvent;
public record CourseCreatedEvent(Long courseId, String code, Instant occurredAt)
        implements DomainEvent {}
