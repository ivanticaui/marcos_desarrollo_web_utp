package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ProgressItem REST resource")
public record ProgressItemResource(Long id, Long tutorialId, String status, Instant startedAt, Instant completedAt) {}
