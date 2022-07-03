package com.cdbros.openlog.feature.logcore.service;

import com.cdbros.openlog.exception.LogcoreException;
import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.util.FakeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
        var fakeMultipart = FakeData.aValidMultipart();
        logcoreService.uploadCsvLogFile(fakeMultipart);
        Mockito.verify(logcoreRepository, Mockito.times(1)).saveAll(Mockito.anyIterable());
    }

    @Test
    void shouldNotUploadCsvLogFile() {
        try {
            var fakeMultipart = FakeData.aNotValidMultipart();
            logcoreService.uploadCsvLogFile(fakeMultipart);
        }
        catch (Exception e) {
            Assertions.assertEquals("Error reading csv file", e.getMessage());
        }
        Mockito.verify(logcoreRepository, Mockito.never()).saveAll(Mockito.anyIterable());
    }

    @Test
    void shouldSaveLogLines() {
        var logcoreDtos = FakeData.aValidLogcoreDtoList();
        logcoreService.saveLogLines(logcoreDtos);
        Mockito.verify(logcoreRepository, Mockito.times(1)).saveAll(Mockito.anyIterable());
    }

    @Test
    void shouldNotSaveLogLines() {
        var logcoreDtos = FakeData.aValidLogcoreDtoList();
        Mockito.when(logcoreRepository.saveAll(Mockito.anyIterable())).thenThrow(LogcoreException.class);
        try {
            logcoreService.saveLogLines(logcoreDtos);
        }
        catch (Exception e) {
            Assertions.assertEquals("Error saving log lines", e.getMessage());
        }
    }
}
