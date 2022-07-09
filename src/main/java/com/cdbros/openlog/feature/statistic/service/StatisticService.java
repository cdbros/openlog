package com.cdbros.openlog.feature.statistic.service;

import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogRequest;
import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.feature.logcore.repository.LogcoreSpecification;
import com.cdbros.openlog.feature.logcore.service.mapper.LogcoreMapper;
import com.cdbros.openlog.model.LogcoreEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticService {

    private final LogcoreRepository logcoreRepository;

    private final LogcoreSpecification logcoreSpecification;

    @Autowired
    public StatisticService(LogcoreRepository logcoreRepository,
                            LogcoreSpecification logcoreSpecification) {
        this.logcoreRepository = logcoreRepository;
        this.logcoreSpecification = logcoreSpecification;
    }

    public PaginatedLogDto getAllLogs(PaginatedLogRequest logRequest) {
        log.info("Getting paginated logs {}", logRequest.toString());
        PageRequest pageRequest = buildPageRequest(logRequest);

        Specification<LogcoreEntity> logSpecification = logcoreSpecification.filterLogs(logRequest);
        Page<LogcoreEntity> pages = logcoreRepository.findAll(logSpecification, pageRequest);

        return LogcoreMapper.toPaginatedLogDto(pages);
    }

    private PageRequest buildPageRequest(PaginatedLogRequest logRequest) {
        if (logRequest.getOrderBy() != null) {
            return PageRequest.of(
                    logRequest.getPage(),
                    logRequest.getSize(),
                    Sort.by(logRequest.isAscOrder() ? Sort.Direction.ASC : Sort.Direction.DESC, logRequest.getOrderBy()));
        }
        else {
            return PageRequest.of(
                    logRequest.getPage(),
                    logRequest.getSize());
        }
    }
}
