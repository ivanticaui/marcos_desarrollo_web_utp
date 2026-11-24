package com.utp.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "CreateStudent REST resource")
public record CreateStudentResource(@NotBlank String firstName, @NotBlank String lastName, @NotBlank @Email String email) {}
