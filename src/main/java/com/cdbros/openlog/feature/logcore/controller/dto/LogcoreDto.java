package com.cdbros.openlog.feature.logcore.controller.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogcoreDto {

    @CsvBindByName
    @NotNull(message = "Project id cannot be null")
    Long projectId;

    @CsvBindByName
    String hostname;

    @CsvBindByName
    String date;

    @CsvBindByName
    String severity;

    @CsvBindByName
    String code;

    @CsvBindByName
    String action;

    @CsvBindByName
    String message;
}
