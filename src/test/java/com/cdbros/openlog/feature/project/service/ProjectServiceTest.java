package com.cdbros.openlog.feature.project.service;

import com.cdbros.openlog.exception.ProjectException;
import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.feature.project.repository.ProjectRepository;
import com.cdbros.openlog.util.FakeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {ProjectRepository.class, ProjectService.class})
class ProjectServiceTest {

    @MockBean
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
        var project = FakeData.aValidProjectDto();
        var projectEntity = FakeData.aValidProjectEntity();

        Mockito.when(projectRepository.save(projectEntity)).thenReturn(projectEntity);
        ProjectDto savedProject = projectService.postProject(project);

        Assertions.assertEquals(savedProject, project);
    }

    @Test
    void shouldNotPostProject() {
        var project = FakeData.aValidProjectDto();
        var projectEntity = FakeData.aValidProjectEntity();

        Mockito.when(projectRepository.save(projectEntity)).thenThrow(ProjectException.class);

        try {
            projectService.postProject(project);
        }
        catch (Exception e) {
            Assertions.assertEquals("Error saving new project", e.getMessage());
        }
    }
}
