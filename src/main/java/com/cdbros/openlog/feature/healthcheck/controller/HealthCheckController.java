package com.cdbros.openlog.feature.healthcheck.controller;

import com.cdbros.openlog.exception.GenericServerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/openlog/api/v1/healthcheck")
    void healthCheckOk() {
    }

    @PostMapping("/openlog/api/v1/healthcheck")
    void healthCheckKo() {
        throw new GenericServerException("HEALTH_CHECK_KO");
    }
}
