package com.cdbros.openlog.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static Integer getHour(String dateString) {
        Timestamp timestamp = getTimestampFromString(dateString);
        return timestamp != null ? (Utils.getHour(timestamp)) : null;
    }

    public static int getHour(Timestamp date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getHour();
    }

    @SneakyThrows
    public static int compareDateString(String s1, String s2, String datePattern) {
        Date d1 = new SimpleDateFormat(datePattern).parse(s1);
        Date d2 = new SimpleDateFormat(datePattern).parse(s2);
        return d1.compareTo(d2);
    }

}
