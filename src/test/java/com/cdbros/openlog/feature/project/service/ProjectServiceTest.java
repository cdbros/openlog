package com.cdbros.openlog.feature.project.service;

import com.cdbros.openlog.exception.ProjectException;
import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.feature.project.repository.ProjectRepository;
import com.cdbros.openlog.model.ProjectEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void shouldGetAllProjects() {
        projectService.getAllProjects();
        Mockito.verify(projectRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldPostProject() {
        var project = ProjectDto.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();
        var projectEntity = ProjectEntity.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();

        Mockito.when(projectRepository.save(projectEntity)).thenReturn(projectEntity);
        ProjectDto savedProject = projectService.postProject(project);

        Assertions.assertEquals(savedProject, project);
    }

    @Test
    void shouldNotPostProject() {
        var project = ProjectDto.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();
        var projectEntity = ProjectEntity.builder()
                .id(1L)
                .name("test1")
                .description("test desc1")
                .build();

        Mockito.when(projectRepository.save(projectEntity)).thenThrow(ProjectException.class);

        try {
            projectService.postProject(project);
        }
        catch (Exception e) {
            Assertions.assertEquals("Error saving new project", e.getMessage());
        }
    }
}
