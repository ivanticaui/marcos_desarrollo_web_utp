package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "RequestEnrollment REST resource")
public record RequestEnrollmentResource(@NotNull @Positive Long studentId, @NotNull @Positive Long courseId) {}
