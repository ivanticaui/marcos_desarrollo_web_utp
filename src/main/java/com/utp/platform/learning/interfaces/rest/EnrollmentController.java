package com.utp.platform.learning.interfaces.rest;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.application.queries.*;
import com.utp.platform.learning.application.commandservices.EnrollmentCommandService;
import com.utp.platform.learning.application.queryservices.EnrollmentQueryService;
import com.utp.platform.learning.interfaces.rest.resources.*;
import com.utp.platform.learning.interfaces.rest.transform.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollment")
@Tag(name = "Enrollment", description = "Enrollment management")
@ApiResponse(responseCode = "400", description = "Invalid request")
@ApiResponse(responseCode = "404", description = "Resource not found")
@ApiResponse(responseCode = "409", description = "Business or persistence conflict")
public class EnrollmentController {
    private final EnrollmentCommandService commands;
    private final EnrollmentQueryService queries;
    public EnrollmentController(EnrollmentCommandService commands, EnrollmentQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @Operation(summary = "Create enrollment")
    @ApiResponse(responseCode = "201", description = "Success")
    public ResponseEntity<EnrollmentResource> create(@Valid @RequestBody RequestEnrollmentResource request) {
        var result = EnrollmentResourceAssembler.toResource(commands.handle(new RequestEnrollmentCommand(request.studentId(), request.courseId())));
        return ResponseEntity.created(URI.create("/api/v1/enrollment/" + result.id())).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    @ApiResponse(responseCode = "200", description = "Success")
    public EnrollmentResource get(@PathVariable @Positive Long id) {
        return EnrollmentResourceAssembler.toResource(queries.handle(new GetEnrollmentByIdQuery(id)));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm enrollment")
    @ApiResponse(responseCode = "200", description = "Success")
    public EnrollmentResource confirm(@PathVariable @Positive Long id) {
        return EnrollmentResourceAssembler.toResource(commands.handle(new ConfirmEnrollmentCommand(id)));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel enrollment")
    @ApiResponse(responseCode = "200", description = "Success")
    public EnrollmentResource cancel(@PathVariable @Positive Long id) {
        return EnrollmentResourceAssembler.toResource(commands.handle(new CancelEnrollmentCommand(id)));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject enrollment")
    @ApiResponse(responseCode = "200", description = "Success")
    public EnrollmentResource reject(@PathVariable @Positive Long id) {
        return EnrollmentResourceAssembler.toResource(commands.handle(new RejectEnrollmentCommand(id)));
    }

    @GetMapping("/by-student/{id}")
    @Operation(summary = "List enrollments by student")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<EnrollmentResource> byStudent(@PathVariable @Positive Long id) {
        return queries.handle(new GetEnrollmentsByStudentQuery(id)).stream().map(EnrollmentResourceAssembler::toResource).toList();
    }

    @GetMapping("/by-course/{id}")
    @Operation(summary = "List enrollments by course")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<EnrollmentResource> byCourse(@PathVariable @Positive Long id) {
        return queries.handle(new GetEnrollmentsByCourseQuery(id)).stream().map(EnrollmentResourceAssembler::toResource).toList();
    }
}
