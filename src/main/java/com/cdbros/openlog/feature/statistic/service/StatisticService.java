package com.cdbros.openlog.feature.statistic.service;

import com.cdbros.openlog.exception.LogcoreException;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogRequest;
import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.feature.logcore.repository.LogcoreSpecification;
import com.cdbros.openlog.feature.logcore.service.mapper.LogcoreMapper;
import com.cdbros.openlog.feature.statistic.controller.dto.ErrorStatsDto;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            return PageRequest.of(logRequest.getPage(),
                    logRequest.getSize(),
                    Sort.by(logRequest.isAscOrder() ? Sort.Direction.ASC : Sort.Direction.DESC, logRequest.getOrderBy()));
        }
        else {
            return PageRequest.of(logRequest.getPage(),
                    logRequest.getSize());
        }
    }

    public List<ErrorStatsDto> getLastTwentyFourHoursErrors(Long projectId, String startDate) {
        try {
            log.info("Getting last 24 hours error log stats");
            Specification<LogcoreEntity> logSpecification = logcoreSpecification.getLastTwentyFourHoursErrors(projectId, startDate);
            List<ErrorStatsDto> errorStatsDtos = new ArrayList<>();
            logcoreRepository.findAll(logSpecification)
                    .stream()
                    .map(LogcoreMapper::toDto)
                    .collect(Collectors.groupingBy(dto -> Utils.getHour(dto.getDate()), Collectors.counting()))
                    .forEach((date, count) -> errorStatsDtos.add(ErrorStatsDto.builder()
                            .time(date)
                            .numberOfErrors(count)
                            .build()));

            // fill empty hours
            for (int i = 1; i <= 24; i++) {
                List<Integer> times = errorStatsDtos.stream().map(ErrorStatsDto::getTime).collect(Collectors.toList());
                if (!times.contains(i)) {
                    errorStatsDtos.add(ErrorStatsDto.builder()
                            .time(i)
                            .numberOfErrors(0)
                            .build());
                }
            }
            errorStatsDtos.sort(Comparator.comparing(ErrorStatsDto::getTime));
            return errorStatsDtos;
        }
        catch (Exception e) {
            throw new LogcoreException("Error getting last 24 hours error log stats");
        }
    }

}
