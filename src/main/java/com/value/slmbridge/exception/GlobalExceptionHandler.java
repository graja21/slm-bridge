package com.value.slmbridge.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        if (message == null || message.isBlank()) {
            message = "Request failed.";
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (message.toLowerCase().contains("verify your email")) {
            status = HttpStatus.FORBIDDEN;
        }

        if (message.toLowerCase().contains("not found")) {
            status = HttpStatus.NOT_FOUND;
        }

        if (message.toLowerCase().contains("expired")) {
            status = HttpStatus.GONE;
        }

        return ResponseEntity
                .status(status)
                .body(Map.of("message", message));
    }
}