package com.cdbros.openlog.feature.logcore.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedLogDto {

    int totalPages;

    int currentPage;

    long totalElements;

    int size;

    List<LogcoreDto> logs;
}
