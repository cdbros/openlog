package com.cdbros.openlog.feature.statistic.controller;

import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogDto;
import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogRequest;
import com.cdbros.openlog.feature.statistic.controller.dto.ErrorStatsDto;
import com.cdbros.openlog.feature.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/openlog/api/v1/logs")
    PaginatedLogDto getAllLogs(@Valid PaginatedLogRequest logRequest) {
        return statisticService.getAllLogs(logRequest);
    }

    @GetMapping("/openlog/api/v1/logs/lastErrors")
    List<ErrorStatsDto> getLastTwentyFourHoursErrors(@Valid @NotNull @RequestParam("projectId") Long projectId,
                                                     @Valid @NotNull @RequestParam("startDate") String startDate) {
        return statisticService.getLastTwentyFourHoursErrors(projectId, startDate);
    }

}
