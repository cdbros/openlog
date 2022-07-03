package com.cdbros.openlog.feature.logcore.service.mapper;

import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreDto;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.Utils;

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
}
