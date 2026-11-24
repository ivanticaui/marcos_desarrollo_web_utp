package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enrollment REST resource")
public record EnrollmentResource(Long id, Long studentId, Long courseId, String status, Instant requestedAt, Instant confirmedAt, Instant cancelledAt, int progressPercentage, List<ProgressItemResource> progressItems) {}
