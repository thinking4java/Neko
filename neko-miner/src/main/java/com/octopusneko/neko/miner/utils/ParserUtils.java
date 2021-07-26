package com.octopusneko.neko.miner.utils;

import org.springframework.util.ObjectUtils;

public class ParserUtils {
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

    public static float parseFloat(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return -99f;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return -99f;
        }
    }
}
