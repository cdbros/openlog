package com.cdbros.openlog.feature.statistic.controller;

import com.cdbros.openlog.feature.statistic.service.StatisticService;
import com.cdbros.openlog.util.FakeData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatisticController.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticService statisticService;

    @Test
    void shouldGetAllLogs() throws Exception {
        var aValidPaginatedLogRequest = FakeData.aValidPaginatedLogRequest();
        var aValidPaginatedLogDto = FakeData.aValidPaginatedLogDto();

        Mockito.when(statisticService.getAllLogs(aValidPaginatedLogRequest)).thenReturn(aValidPaginatedLogDto);

        mvc.perform(get("/openlog/api/v1/logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("projectId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.size", is(4)))
                .andExpect(jsonPath("$.logs", hasSize(4)));
    }

    @Test
    void shouldGetAllLogsWithBadRequest() throws Exception {
        mvc.perform(get("/openlog/api/v1/logs")
                        .param("projectId", ""))
                .andExpect(status().isBadRequest());
    }
}
