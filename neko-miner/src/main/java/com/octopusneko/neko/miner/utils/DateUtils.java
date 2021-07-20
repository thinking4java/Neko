package com.octopusneko.neko.miner.utils;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime parseToLocalTime(String localDateTime, String pattern) {
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static ZonedDateTime parseToUTCTime(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime zonedDateTime;
        try {
            zonedDateTime = ZonedDateTime.parse(date, formatter);
        } catch (DateTimeException e) {
            // couldn't parse to a ZoneDateTime, try LocalDateTime
            LocalDateTime dt = LocalDateTime.parse(date, formatter);

            // convert to a timezone
            zonedDateTime = dt.atZone(ZoneId.of("UTC"));
        }
        return zonedDateTime;
    }

}
