package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Handicap;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.utils.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class HandicapParserImpl implements IHandicapParser {

    @Override
    public List<Handicap> parse(final Match match, int providerId, String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Element element = Jsoup.parse(data).selectFirst("table#hTable");
        if (element != null) {
            List<Handicap> handicaps = new ArrayList<>();
            element.select("tr").forEach(tr -> {
                Elements tds = tr.select("td");
                Handicap handicap = parseHandicap(match, providerId, tds);
                handicaps.add(handicap);
            });
            return handicaps;
        }
        return Collections.emptyList();
    }

    private Handicap parseHandicap(Match match, int providerId, Elements tds) {
        if (tds.size() >= 4) {
            Handicap handicap = new Handicap();
            handicap.setMatch(match);
            handicap.setProviderId(providerId);
            handicap.setHome(parseFloat(tds.get(0).html()));
            handicap.setHandicap(parseHandicap(tds.get(1).html()));
            handicap.setAway(parseFloat(tds.get(2).html()));
            String strTime = String.format("%d-%s", match.getMatchTime().getYear(), tds.get(3).html());
            ZonedDateTime updateTime = DateUtils.parseToUTCTime(strTime, "yyyy-MM-dd HH:mm");
            handicap.setUpdateTime(updateTime);
            handicap.setId(String.format("%d-%d-%d", match.getId(), providerId, updateTime.toEpochSecond()));
            return handicap;
        }
        throw new IllegalArgumentException("Invalid td elements");
    }

    private static float parseHandicap(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return -99f;
        }
        if (str.contains("/")) {
            String[] split = str.split("/");
            if (split.length >= 2) {
                float a = parseFloat(split[0]);
                float b = parseFloat(split[1]);
                boolean negative = a < 0;
                float value = (Math.abs(a) + b) / 2;
                return negative ? -value : value;
            }
        }
        return parseFloat(str);
    }

    private static float parseFloat(String str) {
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
