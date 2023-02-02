package com.interview.drone.backend.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.ValidationException;
import lombok.Builder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationError>> handleValidationErrors(ConstraintViolationException exception) {
        List<ValidationError> errors = exception.getConstraintViolations()
                .stream()
                .map(violation ->
                        ValidationError.builder()
                                .field(
                                        StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                                .peek(node -> System.out.println(node.getKind() + " --------- " + node.getName()))
                                                .map(Path.Node::getName)
                                                .findAny()
                                                .orElse("null"))
                                .error(violation.getMessage())
                                .build())
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableError(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getLocalizedMessage()));
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDataAccessApiUsageError(InvalidDataAccessApiUsageException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getLocalizedMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(ValidationException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getLocalizedMessage()));
    }

    @Builder
    private record ValidationError(String field, String error) {
    }

}
