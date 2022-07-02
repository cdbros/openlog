package com.cdbros.openlog.feature.project.service;

import com.cdbros.openlog.exception.ProjectException;
import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.feature.project.repository.ProjectRepository;
import com.cdbros.openlog.feature.project.service.mapper.ProjectMapper;
import com.cdbros.openlog.model.ProjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDto> getAllProjects() {
        log.info("Getting all projects from database...");
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toProjectDto)
                .collect(Collectors.toList());
    }

    public ProjectDto postProject(ProjectDto projectDto) {
        try {
            log.info("Saving project {}...", projectDto.getName());
            ProjectEntity projectEntity = ProjectMapper.toProjectEntity(projectDto);
            ProjectEntity newProjectEntity = projectRepository.save(projectEntity);
            return ProjectMapper.toProjectDto(newProjectEntity);
        }
        catch (Exception e) {
            throw new ProjectException("Error saving new project");
        }
    }

}
