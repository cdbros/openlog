package com.cdbros.openlog.feature.project.controller;

import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.feature.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/openlog/api/v1/project")
    List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/openlog/api/v1/project")
    ProjectDto postProject(@Valid @RequestBody ProjectDto projectDto) {
        return projectService.postProject(projectDto);
    }

}
