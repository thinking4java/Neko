package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.OverUnder;
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
public class OverUnderParserImpl implements IOverUnderParser {

    @Override
    public List<OverUnder> parse(final Match match, int providerId, String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Element element = Jsoup.parse(data).selectFirst("table#hTable");
        if (element != null) {
            List<OverUnder> overUnderList = new ArrayList<>();
            element.select("tr").forEach(tr -> {
                Elements tds = tr.select("td");
                OverUnder overUnder = parseOverUnder(match, providerId, tds);
                overUnderList.add(overUnder);
            });
            return overUnderList;
        }
        return Collections.emptyList();
    }

    private OverUnder parseOverUnder(Match match, int providerId, Elements tds) {
        if (tds.size() >= 4) {
            OverUnder overUnder = new OverUnder();
            overUnder.setMatch(match);
            overUnder.setProviderId(providerId);
            overUnder.setHome(ParserUtils.parseBigDecimal(tds.get(0).html()));
            overUnder.setOverUnder(parseOverUnder(tds.get(1).html()));
            overUnder.setAway(ParserUtils.parseBigDecimal(tds.get(2).html()));
            String strTime = String.format("%d-%s", match.getMatchTime().getYear(), tds.get(3).html());
            ZonedDateTime updateTime = DateUtils.parseToUTCTime(strTime, "yyyy-MM-dd HH:mm");
            overUnder.setUpdateTime(updateTime);
            overUnder.setId(String.format("%d-%d-%d", match.getId(), providerId, updateTime.toEpochSecond()));
            return overUnder;
        }
        throw new IllegalArgumentException("Invalid td elements");
    }

    private static BigDecimal parseOverUnder(String str) {
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
