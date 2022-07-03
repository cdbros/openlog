package com.cdbros.openlog.feature.project.controller;

import com.cdbros.openlog.exception.ProjectException;
import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.feature.project.service.ProjectService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Test
    void shouldGetAllProjects() throws Exception {
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

        Mockito.when(projectService.getAllProjects()).thenReturn(projectDtoList);

        mvc.perform(get("/openlog/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("test1")))
                .andExpect(jsonPath("$.[0].description", is("test desc1")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].name", is("test2")))
                .andExpect(jsonPath("$.[1].description", is("test desc2")));
    }

    @Test
    void shouldPostProject() throws Exception {
        var project = ProjectDto.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();

        Mockito.when(projectService.postProject(project)).thenReturn(project);

        mvc.perform(post("/openlog/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test1")))
                .andExpect(jsonPath("$.description", is("test desc1")));
    }

    @Test
    void shouldNotPostProject_withBadRequest() throws Exception {
        var project = ProjectDto.builder()
                .id(1L)
                .name("")
                .description("test desc1")
                .build();

        Mockito.when(projectService.postProject(project)).thenReturn(project);

        mvc.perform(post("/openlog/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotPostProject_withProjectException() throws Exception {
        var project = ProjectDto.builder()
                .id(1L)
                .name("test")
                .description("test desc1")
                .build();

        Mockito.when(projectService.postProject(project)).thenThrow(ProjectException.class);

        mvc.perform(post("/openlog/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isInternalServerError());
    }
}
