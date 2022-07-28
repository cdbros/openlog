package com.cdbros.openlog.feature.statistic.controller.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorStatsDto {

    Integer time;

    long numberOfErrors;
}
