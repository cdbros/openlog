package com.cdbros.openlog.feature.logcore.controller.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogcoreCsvBean {

    @CsvBindByName
    Long projectId;

    @CsvBindByName
    String hostname;

    @CsvBindByName
    String date;

    @CsvBindByName
    String severity;

    @CsvBindByName
    String code;

    @CsvBindByName
    String action;

    @CsvBindByName
    String message;
}
