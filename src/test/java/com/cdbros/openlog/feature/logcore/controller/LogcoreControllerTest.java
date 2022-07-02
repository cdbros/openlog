package com.cdbros.openlog.feature.logcore.controller;

import com.cdbros.openlog.exception.LogcoreException;
import com.cdbros.openlog.feature.logcore.service.LogcoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogcoreController.class)
class LogcoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogcoreService logcoreService;

    @Test
    void shouldUploadLogFile() throws Exception {
        var fakeMultipart = new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                "some,data".getBytes());

        mvc.perform(multipart("/openlog/api/v1/logcore")
                        .file(fakeMultipart))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUploadLogFile_withInternalError() throws Exception {
        var fakeMultipart = new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                "some,data".getBytes());

        Mockito.doThrow(LogcoreException.class)
                .when(logcoreService)
                .uploadCsvLogFile(fakeMultipart);

        mvc.perform(multipart("/openlog/api/v1/logcore")
                        .file(fakeMultipart))
                .andExpect(status().isInternalServerError());
    }
}
