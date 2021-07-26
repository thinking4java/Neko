package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Odds;
import com.octopusneko.neko.miner.utils.DateUtils;
import com.octopusneko.neko.miner.utils.ParserUtils;
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
public class OddsParserImpl implements IOddsParser {

    @Override
    public List<Odds> parse(final Match match, int providerId, String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Element element = Jsoup.parse(data).selectFirst("table[class=\"mytable2\"]");
        if (element != null) {
            List<Odds> oddsList = new ArrayList<>();
            element.select("tr").forEach(tr -> {
                Elements tds = tr.select("td");
                if(tds.size() < 6) {
                    return;
                }
                Odds overUnder = parseOdds(match, providerId, tds);
                oddsList.add(overUnder);
            });
            return oddsList;
        }
        return Collections.emptyList();
    }

    private Odds parseOdds(Match match, int providerId, Elements tds) {
        if (tds.size() >= 6) {
            Odds odds = new Odds();
            odds.setMatch(match);
            odds.setProviderId(providerId);
            odds.setHome(ParserUtils.parseFloat(tds.get(0).html()));
            odds.setDraw(ParserUtils.parseFloat(tds.get(1).html()));
            odds.setAway(ParserUtils.parseFloat(tds.get(2).html()));
            String strTime = String.format("%d-%s", match.getMatchTime().getYear(), tds.get(5).text());
            ZonedDateTime updateTime = DateUtils.parseToUTCTime(strTime, "yyyy-MM-dd HH:mm");
            odds.setUpdateTime(updateTime);
            odds.setId(String.format("%d-%d-%d", match.getId(), providerId, updateTime.toEpochSecond()));
            return odds;
        }
        throw new IllegalArgumentException("Invalid td elements");
    }
}
