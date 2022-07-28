package com.cdbros.openlog.feature.statistic.service;

import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.feature.logcore.repository.LogcoreSpecification;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.FakeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@SpringBootTest(classes = {LogcoreRepository.class, LogcoreSpecification.class, StatisticService.class})
class StatisticServiceTest {

    @MockBean
    private LogcoreRepository logcoreRepository;

    @MockBean
    private LogcoreSpecification logcoreSpecification;

    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        statisticService = new StatisticService(logcoreRepository, logcoreSpecification);
    }

    @Test
    void shouldGetAllLogs() {
        var aValidPaginatedLogRequest = FakeData.aValidPaginatedLogRequest();
        var aValidPaginatedLogDto = FakeData.aValidPaginatedLogDto();
        var pageRequest = PageRequest.of(aValidPaginatedLogRequest.getPage(), aValidPaginatedLogRequest.getSize());
        Page<LogcoreEntity> logcoreEntities = new PageImpl<>(FakeData.aValidLogcoreEntityList());

        Mockito.when(logcoreRepository.findAll(logcoreSpecification.filterLogs(aValidPaginatedLogRequest), pageRequest))
                .thenReturn(logcoreEntities);

        Assertions.assertEquals(statisticService.getAllLogs(aValidPaginatedLogRequest), aValidPaginatedLogDto);
    }

    @Test
    void shouldGetLastTwentyFourHoursErrors() {
        var aValidLogcoreEntityList = FakeData.aValidLogcoreEntityListTwo();
        var aValidErrorStatsList = FakeData.aValidErrorStatsListTwo();
        Specification<LogcoreEntity> logSpecification = logcoreSpecification.getLastTwentyFourHoursErrors(1L, "2022-06-30 00:00:00.0");

        Mockito.when(logcoreRepository.findAll(logSpecification)).thenReturn(aValidLogcoreEntityList);

        Assertions.assertEquals(statisticService.getLastTwentyFourHoursErrors(1L, "2022-06-30 00:00:00.0"), aValidErrorStatsList);
    }
}
