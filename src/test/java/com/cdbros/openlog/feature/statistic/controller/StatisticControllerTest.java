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

    @Test
    void shouldGetLastTwentyFourHoursErrors() throws Exception {

        var aValidErrorStatsList = FakeData.aValidErrorStatsList();

        Mockito.when(statisticService.getLastTwentyFourHoursErrors(1L, "2022-06-28 00:00:00.0"))
                .thenReturn(aValidErrorStatsList);

        mvc.perform(get("/openlog/api/v1/logs/lastErrors")
                        .param("projectId", "1")
                        .param("startDate", "2022-06-28 00:00:00.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].time", is(1)))
                .andExpect(jsonPath("$.[0].numberOfErrors", is(10)))
                .andExpect(jsonPath("$.[1].time", is(2)))
                .andExpect(jsonPath("$.[1].numberOfErrors", is(5)))
                .andExpect(jsonPath("$.[2].time", is(3)))
                .andExpect(jsonPath("$.[2].numberOfErrors", is(0)));
    }

    @Test
    void shouldGetLastTwentyFourHoursErrorsWithBadRequest() throws Exception {
        mvc.perform(get("/openlog/api/v1/logs/lastErrors")
                        .param("projectId", "")
                        .param("startDate", "2022-06-28 00:00:00.0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetLastTwentyFourHoursErrorsWithBadRequest2() throws Exception {
        mvc.perform(get("/openlog/api/v1/logs/lastErrors")
                        .param("projectId", "1")
                        .param("startDate", (String) null))
                .andExpect(status().isBadRequest());
    }
}
