package com.cdbros.openlog.feature.project.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {

    Long id;

    @NotBlank(message = "Project name cannot be empty or null")
    String name;

    String description;
}
