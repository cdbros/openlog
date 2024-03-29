package com.cdbros.openlog.feature.logcore.controller;

import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreDto;
import com.cdbros.openlog.feature.logcore.service.LogcoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class LogcoreController {

    private final LogcoreService logcoreService;

    @Autowired
    public LogcoreController(LogcoreService logcoreService) {
        this.logcoreService = logcoreService;
    }

    @PostMapping("/openlog/api/v1/logcore")
    @ResponseStatus(HttpStatus.CREATED)
    void saveLogLines(@Valid @RequestBody List<LogcoreDto> logcoreDtos) {
        logcoreService.saveLogLines(logcoreDtos);
    }

    @PostMapping("/openlog/api/v1/logcore/csv")
    @ResponseStatus(HttpStatus.CREATED)
    void uploadLogFile(@RequestParam("logfile") @NotNull MultipartFile logfile) {
        logcoreService.uploadCsvLogFile(logfile);
    }
}
