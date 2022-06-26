package com.cdbros.openlog.healthcheck.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class HealthCheckControllerTest {

    @Autowired
    private HealthCheckController healthCheckController;
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();
    }

    @Test
    void shouldHealthCheckOk() throws Exception {
        mvc.perform(get("/openlog/api/v1/healthcheck"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldHealthCheckKo() throws Exception {
        mvc.perform(post("/openlog/api/v1/healthcheck"))
                .andExpect(status().is(500));
    }
}
