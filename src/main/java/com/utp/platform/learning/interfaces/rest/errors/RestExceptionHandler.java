package com.utp.platform.learning.interfaces.rest.errors;

import com.utp.platform.learning.domain.exceptions.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({StudentNotFoundException.class, CourseNotFoundException.class, EnrollmentNotFoundException.class})
    public ProblemDetail notFound(RuntimeException ex) { return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()); }
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail invalid(IllegalArgumentException ex) { return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail conflict(IllegalStateException ex) { return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage()); }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail persistenceConflict() { return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Data integrity constraint violated"); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
        problem.setProperty("errors", ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList());
        return problem;
    }
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail pathValidation() { return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid path parameter"); }
}
