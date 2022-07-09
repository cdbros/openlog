package com.cdbros.openlog.util;

import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogRequest;
import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.model.ProjectEntity;
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
                .date("2022-06-30 17:00:00")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-29 17:00:00")
                .severity("ERROR")
                .build());

        return logcoreDtos;
    }

    public static List<LogcoreDto> aNotValidLogcoreDtoList() {
        List<LogcoreDto> logcoreDtos = new ArrayList<>();
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-30 17:00:00")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(null)
                .date("2022-06-29 17:00:00")
                .severity("ERROR")
                .build());

        return logcoreDtos;
    }

    public static List<LogcoreEntity> aValidLogcoreEntityList() {
        List<LogcoreEntity> logcoreEntities = new ArrayList<>();
        logcoreEntities.add(LogcoreEntity.builder()
                .projectId(1L)
                .date(Utils.getTimestampFromString("2022-06-30 17:00:00.0"))
                .severity("ERROR")
                .build());
        logcoreEntities.add(LogcoreEntity.builder()
                .projectId(1L)
                .date(Utils.getTimestampFromString("2022-06-29 17:00:00.0"))
                .severity("ERROR")
                .build());
        logcoreEntities.add(LogcoreEntity.builder()
                .projectId(1L)
                .date(Utils.getTimestampFromString("2022-06-28 17:00:00.0"))
                .severity("ERROR")
                .build());
        logcoreEntities.add(LogcoreEntity.builder()
                .projectId(1L)
                .date(Utils.getTimestampFromString("2022-06-27 17:00:00.0"))
                .severity("ERROR")
                .build());

        return logcoreEntities;
    }

    public static List<ProjectDto> aValidProjectDtoList() {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        projectDtoList.add(ProjectDto.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build());
        projectDtoList.add(ProjectDto.builder()
                .id(2L)
                .name("test2")
                .description("test desc2")
                .build());

        return projectDtoList;
    }

    public static ProjectDto aValidProjectDto() {
        return ProjectDto.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();
    }

    public static ProjectDto aNotValidProjectDto() {
        return ProjectDto.builder()
                .id(1L)
                .name("")
                .description("test desc1")
                .build();
    }

    public static ProjectEntity aValidProjectEntity() {
        return ProjectEntity.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();
    }

    public static PaginatedLogDto aValidPaginatedLogDto() {
        List<LogcoreDto> logcoreDtos = new ArrayList<>();
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-30 17:00:00.0")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-29 17:00:00.0")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-28 17:00:00.0")
                .severity("ERROR")
                .build());
        logcoreDtos.add(LogcoreDto.builder()
                .projectId(1L)
                .date("2022-06-27 17:00:00.0")
                .severity("ERROR")
                .build());

        return PaginatedLogDto.builder()
                .size(4)
                .currentPage(0)
                .totalPages(1)
                .totalElements(4)
                .logs(logcoreDtos)
                .build();
    }

    public static PaginatedLogRequest aValidPaginatedLogRequest() {
        return PaginatedLogRequest.builder()
                .projectId(1L)
                .page(0)
                .build();
    }

}
