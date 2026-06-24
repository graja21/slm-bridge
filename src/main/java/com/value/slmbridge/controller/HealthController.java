package com.value.slmbridge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, Object> health() {

        return Map.of(
                "service", "slm-bridge",
                "status", "UP",
                "timestamp", LocalDateTime.now()
        );
    }
}