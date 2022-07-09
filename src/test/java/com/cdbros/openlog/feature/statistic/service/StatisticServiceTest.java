package com.cdbros.openlog.feature.statistic.service;

import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.feature.logcore.repository.LogcoreSpecification;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.FakeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class StatisticServiceTest {

    @Mock
    private LogcoreRepository logcoreRepository;

    @Mock
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
}
