package com.octopusneko.neko.miner.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private DateUtils() {
        
    }

    public static LocalDateTime parseToLocalTime(String localDateTime, String pattern) {
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static ZonedDateTime parseToUTCTime(String localDateTime, String pattern) {
        return parseToLocalTime(localDateTime, pattern).atZone(ZoneOffset.ofHours(8)).withZoneSameInstant(ZoneOffset.UTC);
    }
}
