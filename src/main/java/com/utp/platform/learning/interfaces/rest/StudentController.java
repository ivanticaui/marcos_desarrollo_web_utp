package com.utp.platform.learning.interfaces.rest;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.application.queries.*;
import com.utp.platform.learning.application.commandservices.StudentCommandService;
import com.utp.platform.learning.application.queryservices.StudentQueryService;
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
@RequestMapping("/api/v1/student")
@Tag(name = "Student", description = "Student management")
@ApiResponse(responseCode = "400", description = "Invalid request")
@ApiResponse(responseCode = "404", description = "Resource not found")
@ApiResponse(responseCode = "409", description = "Business or persistence conflict")
public class StudentController {
    private final StudentCommandService commands;
    private final StudentQueryService queries;
    public StudentController(StudentCommandService commands, StudentQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @Operation(summary = "Create student")
    @ApiResponse(responseCode = "201", description = "Success")
    public ResponseEntity<StudentResource> create(@Valid @RequestBody CreateStudentResource request) {
        var result = StudentResourceAssembler.toResource(commands.handle(new CreateStudentCommand(request.firstName(), request.lastName(), request.email())));
        return ResponseEntity.created(URI.create("/api/v1/student/" + result.id())).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    @ApiResponse(responseCode = "200", description = "Success")
    public StudentResource get(@PathVariable @Positive Long id) {
        return StudentResourceAssembler.toResource(queries.handle(new GetStudentByIdQuery(id)));
    }

    @GetMapping
    @Operation(summary = "List student records")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<StudentResource> list() {
        return queries.findAll().stream().map(StudentResourceAssembler::toResource).toList();
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Change student active status")
    @ApiResponse(responseCode = "200", description = "Success")
    public StudentResource changeActive(@PathVariable @Positive Long id, @Valid @RequestBody ChangeStudentActiveResource request) {
        return StudentResourceAssembler.toResource(commands.handle(new ChangeStudentActiveCommand(id, request.active())));
    }
}
