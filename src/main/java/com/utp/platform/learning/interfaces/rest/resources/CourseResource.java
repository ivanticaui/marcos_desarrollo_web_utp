package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Course REST resource")
public record CourseResource(Long id, String code, String title, String description, int credits, int capacity, boolean active, Instant createdAt, Set<Long> prerequisiteCourseIds, List<Long> tutorialIds) {}
