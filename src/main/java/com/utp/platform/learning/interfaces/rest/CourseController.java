package com.utp.platform.learning.interfaces.rest;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.application.queries.*;
import com.utp.platform.learning.application.commandservices.CourseCommandService;
import com.utp.platform.learning.application.queryservices.CourseQueryService;
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
@RequestMapping("/api/v1/course")
@Tag(name = "Course", description = "Course management")
@ApiResponse(responseCode = "400", description = "Invalid request")
@ApiResponse(responseCode = "404", description = "Resource not found")
@ApiResponse(responseCode = "409", description = "Business or persistence conflict")
public class CourseController {
    private final CourseCommandService commands;
    private final CourseQueryService queries;
    public CourseController(CourseCommandService commands, CourseQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @Operation(summary = "Create course")
    @ApiResponse(responseCode = "201", description = "Success")
    public ResponseEntity<CourseResource> create(@Valid @RequestBody CreateCourseResource request) {
        var result = CourseResourceAssembler.toResource(commands.handle(new CreateCourseCommand(request.code(), request.title(), request.description(), request.credits(), request.capacity())));
        return ResponseEntity.created(URI.create("/api/v1/course/" + result.id())).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    @ApiResponse(responseCode = "200", description = "Success")
    public CourseResource get(@PathVariable @Positive Long id) {
        return CourseResourceAssembler.toResource(queries.handle(new GetCourseByIdQuery(id)));
    }

    @GetMapping
    @Operation(summary = "List course records")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<CourseResource> list() {
        return queries.findAll().stream().map(CourseResourceAssembler::toResource).toList();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course details")
    @ApiResponse(responseCode = "200", description = "Success")
    public CourseResource update(@PathVariable @Positive Long id, @Valid @RequestBody UpdateCourseResource request) {
        return CourseResourceAssembler.toResource(commands.handle(new UpdateCourseCommand(id, request.title(), request.description(), request.credits(), request.capacity())));
    }
}
