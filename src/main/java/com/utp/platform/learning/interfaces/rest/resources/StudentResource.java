package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Student REST resource")
public record StudentResource(Long id, String recordId, String firstName, String lastName, String email, boolean active, Instant createdAt) {}
