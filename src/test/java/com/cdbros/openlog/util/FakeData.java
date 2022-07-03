package com.cdbros.openlog.util;

import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreDto;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FakeData {

    private FakeData() {
    }

    public static MockMultipartFile aValidMultipart() throws IOException {
        InputStream stream = IOUtils.toInputStream("projectId,hostname,date,severity,code,action,message\n1,java_machine,2022-06-30_17:00:00,ERROR,250,api_call,test", StandardCharsets.UTF_8);
        return new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                stream);
    }

    public static MockMultipartFile aNotValidMultipart() throws IOException {
        // the multipart is not valid because doesn't contain the required headers
        InputStream stream = IOUtils.toInputStream("date,severity,code,action,message\n1,java_machine,2022-06-30_17:00:00,ERROR,250,api_call,test", StandardCharsets.UTF_8);
        return new MockMultipartFile("logfile",
                "logfile.txt",
                "text",
                stream);
    }

    public static List<LogcoreDto> aValidLogcoreDtoList() {
        List<LogcoreDto> logcoreDtos = new ArrayList<>();
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-30_17:00:00")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-29_17:00:00")
                .severity("ERROR")
                .build());

        return logcoreDtos;
    }

    public static List<LogcoreDto> aNotValidLogcoreDtoList() {
        List<LogcoreDto> logcoreDtos = new ArrayList<>();
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-30_17:00:00")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(null)
                .date("2022-06-29_17:00:00")
                .severity("ERROR")
                .build());

        return logcoreDtos;
    }

}
