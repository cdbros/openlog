package com.cdbros.openlog.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Utils {

    private Utils() {
    }

    public static Timestamp getTimestampFromString(String date) {
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").parse(date);
            return new Timestamp(parsedDate.getTime());
        }
        catch (Exception e) {
            log.error("Error parsing string to timestamp", e);
            return null;
        }
    }

}
