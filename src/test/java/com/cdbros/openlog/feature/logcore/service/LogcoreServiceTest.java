package com.cdbros.openlog.feature.logcore.service;

import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class LogcoreServiceTest {

    @Mock
    private LogcoreRepository logcoreRepository;

    private LogcoreService logcoreService;

    @BeforeEach
    void setUp() {
        logcoreService = new LogcoreService(logcoreRepository);
    }

    @Test
    void shouldUploadCsvLogFile() throws IOException {
        var fakeMultipart = aValidMultipart();
        logcoreService.uploadCsvLogFile(fakeMultipart);
        Mockito.verify(logcoreRepository, Mockito.times(1)).saveAll(Mockito.anyIterable());
    }

    @Test
    void shouldNotUploadCsvLogFile() {
        try {
            var fakeMultipart = aNotValidMultipart();
            logcoreService.uploadCsvLogFile(fakeMultipart);
        }
        catch (Exception e) {
            Assertions.assertEquals("Error reading csv file", e.getMessage());
        }
        Mockito.verify(logcoreRepository, Mockito.never()).saveAll(Mockito.anyIterable());
    }

    private MockMultipartFile aValidMultipart() throws IOException {
        InputStream stream = IOUtils.toInputStream("projectId,hostname,date,severity,code,action,message\n1,java_machine,2022-06-30_17:00:00,ERROR,250,api_call,test", StandardCharsets.UTF_8);
        return new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                stream);
    }

    private MockMultipartFile aNotValidMultipart() throws IOException {
        // the multipart is not valid because doesn't contain the required headers
        InputStream stream = IOUtils.toInputStream("date,severity,code,action,message\n1,java_machine,2022-06-30_17:00:00,ERROR,250,api_call,test", StandardCharsets.UTF_8);
        return new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                stream);
    }
}
