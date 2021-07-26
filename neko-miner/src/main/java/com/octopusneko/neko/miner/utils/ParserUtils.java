package com.octopusneko.neko.miner.utils;

import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

public final class ParserUtils {

    private ParserUtils() {

    }

    public static int parseInt(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return -99;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -99;
        }
    }

    public static BigDecimal parseBigDecimal(String str) {
        return new BigDecimal(str);
    }
}
