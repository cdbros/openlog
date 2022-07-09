package com.cdbros.openlog.feature.logcore.service.mapper;

import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogDto;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.Utils;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class LogcoreMapper {

    private LogcoreMapper() {
    }

    public static LogcoreEntity toEntity(LogcoreDto logDto) {
        return LogcoreEntity.builder()
                .projectId(logDto.getProjectId())
                .action(logDto.getAction())
                .code(logDto.getCode())
                .date(Utils.getTimestampFromString(logDto.getDate()))
                .hostname(logDto.getHostname())
                .message(logDto.getMessage())
                .severity(logDto.getSeverity())
                .build();
    }

    public static PaginatedLogDto toPaginatedLogDto(Page<LogcoreEntity> logcoreEntities) {
        return PaginatedLogDto.builder()
                .totalPages(logcoreEntities.getTotalPages())
                .currentPage(logcoreEntities.getNumber())
                .size(logcoreEntities.getSize())
                .totalElements(logcoreEntities.getTotalElements())
                .logs(logcoreEntities.get()
                        .map(LogcoreMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static LogcoreDto toDto(LogcoreEntity logcoreEntity) {
        return LogcoreDto.builder()
                .projectId(logcoreEntity.getProjectId())
                .action(logcoreEntity.getAction())
                .code(logcoreEntity.getCode())
                .date(logcoreEntity.getDate().toString())
                .hostname(logcoreEntity.getHostname())
                .message(logcoreEntity.getMessage())
                .severity(logcoreEntity.getSeverity())
                .build();
    }
}
