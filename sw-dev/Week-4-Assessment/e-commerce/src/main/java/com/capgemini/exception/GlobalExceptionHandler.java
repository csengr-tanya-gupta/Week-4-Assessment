package com.capgemini.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(Exception e) {
        return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(Exception e) {
        return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
    }
}