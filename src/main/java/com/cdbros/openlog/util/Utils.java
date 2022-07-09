package com.cdbros.openlog.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Utils {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private Utils() {
    }

    public static Timestamp getTimestampFromString(String date) {
        try {
            Date parsedDate = new SimpleDateFormat(DATE_PATTERN).parse(date);
            return new Timestamp(parsedDate.getTime());
        }
        catch (Exception e) {
            log.error("Error parsing string to timestamp", e);
            return null;
        }
    }

}
