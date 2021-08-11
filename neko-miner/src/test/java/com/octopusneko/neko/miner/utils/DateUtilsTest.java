package com.octopusneko.neko.miner.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void test_parseToLocalTime() {
        LocalDateTime localDateTime = DateUtils.parseToLocalTime("20210810170000", "yyyyMMddHHmmss");
        Assertions.assertEquals(2021, localDateTime.getYear());
        Assertions.assertEquals(8, localDateTime.getMonth().getValue());
        Assertions.assertEquals(10, localDateTime.getDayOfMonth());
        Assertions.assertEquals(17, localDateTime.getHour());
        Assertions.assertEquals(0, localDateTime.getMinute());
        Assertions.assertEquals(0, localDateTime.getSecond());
        System.out.println(localDateTime);
    }

    @Test
    void test_parseToUTCTime() {
        ZonedDateTime zonedDateTime = DateUtils.parseToUTCTime("20210810170000", "yyyyMMddHHmmss");
        Assertions.assertEquals(2021, zonedDateTime.getYear());
        Assertions.assertEquals(8, zonedDateTime.getMonth().getValue());
        Assertions.assertEquals(10, zonedDateTime.getDayOfMonth());
        Assertions.assertEquals(9, zonedDateTime.getHour());
        Assertions.assertEquals(0, zonedDateTime.getMinute());
        Assertions.assertEquals(0, zonedDateTime.getSecond());
        System.out.println(zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }
}