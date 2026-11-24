package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "CreateCourse REST resource")
public record CreateCourseResource(@NotBlank String code, @NotBlank String title, String description, @Min(1) @Max(30) int credits, @Min(1) int capacity) {}
