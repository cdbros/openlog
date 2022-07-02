package com.cdbros.openlog.feature.project.service.mapper;

import com.cdbros.openlog.feature.project.controller.dto.ProjectDto;
import com.cdbros.openlog.model.ProjectEntity;

public class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectEntity toProjectEntity(ProjectDto projectDto) {
        return ProjectEntity.builder()
                .id(projectDto.getId())
                .description(projectDto.getDescription())
                .name(projectDto.getName())
                .build();
    }

    public static ProjectDto toProjectDto(ProjectEntity projectEntity) {
        return ProjectDto.builder()
                .id(projectEntity.getId())
                .name(projectEntity.getName())
                .description(projectEntity.getDescription())
                .build();
    }
}
