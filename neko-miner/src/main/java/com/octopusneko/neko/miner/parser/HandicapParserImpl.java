package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Handicap;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.utils.DateUtils;
import com.octopusneko.neko.miner.utils.ParserUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
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
            List<Handicap> handicapList = new ArrayList<>();
            element.select("tr").forEach(tr -> {
                Elements tds = tr.select("td");
                Handicap handicap = parseHandicap(match, providerId, tds);
                handicapList.add(handicap);
            });
            return handicapList;
        }
        return Collections.emptyList();
    }

    private Handicap parseHandicap(Match match, int providerId, Elements tds) {
        if (tds.size() >= 4) {
            Handicap handicap = new Handicap();
            handicap.setMatch(match);
            handicap.setProviderId(providerId);
            handicap.setHome(ParserUtils.parseBigDecimal(tds.get(0).html()));
            handicap.setHandicap(parseHandicap(tds.get(1).html()));
            handicap.setAway(ParserUtils.parseBigDecimal(tds.get(2).html()));
            String strTime = String.format("%d-%s", match.getMatchTime().getYear(), tds.get(3).html());
            ZonedDateTime updateTime = DateUtils.parseToUTCTime(strTime, "yyyy-MM-dd HH:mm");
            handicap.setUpdateTime(updateTime);
            handicap.setId(String.format("%d-%d-%d", match.getId(), providerId, updateTime.toEpochSecond()));
            return handicap;
        }
        throw new IllegalArgumentException("Invalid td elements");
    }

    private static BigDecimal parseHandicap(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return new BigDecimal(0);
        }
        if (str.contains("/")) {
            String[] split = str.split("/");
            if (split.length >= 2) {
                BigDecimal a = ParserUtils.parseBigDecimal(split[0]);
                BigDecimal b = ParserUtils.parseBigDecimal(split[1]);
                boolean negative = a.compareTo(new BigDecimal(0)) < 0;
                BigDecimal value = a.abs().add(b).divide(new BigDecimal(2));
                return negative ? value.negate() : value;
            }
        }
        return ParserUtils.parseBigDecimal(str);
    }
}
