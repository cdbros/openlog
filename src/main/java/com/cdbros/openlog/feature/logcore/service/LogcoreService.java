package com.cdbros.openlog.feature.logcore.service;

import com.cdbros.openlog.exception.LogcoreException;
import com.cdbros.openlog.feature.logcore.controller.dto.LogcoreCsvBean;
import com.cdbros.openlog.feature.logcore.repository.LogcoreRepository;
import com.cdbros.openlog.feature.logcore.service.mapper.LogcoreMapper;
import com.cdbros.openlog.model.LogcoreEntity;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LogcoreService {

    private final LogcoreRepository logcoreRepository;

    @Autowired
    public LogcoreService(LogcoreRepository logcoreRepository) {
        this.logcoreRepository = logcoreRepository;
    }

    public void uploadCsvLogFile(MultipartFile logFile) {
        try (Reader reader = new BufferedReader(new InputStreamReader(logFile.getInputStream()))) {
            CsvToBean<LogcoreCsvBean> csvToBean = new CsvToBeanBuilder<LogcoreCsvBean>(reader)
                    .withType(LogcoreCsvBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<LogcoreEntity> logsEntities = csvToBean.parse()
                    .stream()
                    .map(LogcoreMapper::toEntity)
                    .collect(Collectors.toList());

            logcoreRepository.saveAll(logsEntities);
        }
        catch (Exception e) {
            throw new LogcoreException("Error reading csv file");
        }
    }
}

