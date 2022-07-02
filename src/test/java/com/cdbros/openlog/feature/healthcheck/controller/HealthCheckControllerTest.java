package com.cdbros.openlog.feature.healthcheck.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HealthCheckController.class)
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mvc;

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
