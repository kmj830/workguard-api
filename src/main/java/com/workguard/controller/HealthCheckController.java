package com.workguard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Tag(name = "Health Check", description = "서버 상태 확인 API")
@RestController
public class HealthCheckController {

    @Operation(summary = "루트 서비스 상태 확인")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        return ResponseEntity.ok(Map.of(
                "service", "workguard-api",
                "status", "UP",
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    @Operation(summary = "간이 헬스체크")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
