package com.cdbros.openlog.feature.logcore.controller;

import com.cdbros.openlog.exception.LogcoreException;
import com.cdbros.openlog.feature.logcore.service.LogcoreService;
import com.cdbros.openlog.util.FakeData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogcoreController.class)
class LogcoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LogcoreService logcoreService;

    @Test
    void shouldUploadLogFile() throws Exception {
        var fakeMultipart = FakeData.aValidMultipart();

        mvc.perform(multipart("/openlog/api/v1/logcore/csv")
                        .file(fakeMultipart))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotUploadLogFile_withInternalError() throws Exception {
        var fakeMultipart = FakeData.aValidMultipart();

        Mockito.doThrow(LogcoreException.class)
                .when(logcoreService)
                .uploadCsvLogFile(fakeMultipart);

        mvc.perform(multipart("/openlog/api/v1/logcore/csv")
                        .file(fakeMultipart))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldSaveLogLines() throws Exception {
        var logcoreDtos = FakeData.aValidLogcoreDtoList();

        mvc.perform(post("/openlog/api/v1/logcore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logcoreDtos)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotSaveLogLines_withBadRequest() throws Exception {
        var logcoreDtos = FakeData.aNotValidLogcoreDtoList();

        mvc.perform(post("/openlog/api/v1/logcore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logcoreDtos)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotSaveLogLines_withInternalError() throws Exception {
        var logcoreDtos = FakeData.aValidLogcoreDtoList();

        Mockito.doThrow(LogcoreException.class)
                .when(logcoreService)
                .saveLogLines(logcoreDtos);

        mvc.perform(post("/openlog/api/v1/logcore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logcoreDtos)))
                .andExpect(status().isInternalServerError());
    }
}
