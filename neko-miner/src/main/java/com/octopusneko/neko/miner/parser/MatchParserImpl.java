package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.MatchState;
import com.octopusneko.neko.miner.utils.DateUtils;
import com.octopusneko.neko.miner.utils.ParserUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MatchParserImpl implements IMatchParser {

    private static final Logger logger = LoggerFactory.getLogger(MatchParserImpl.class);

    private static final Pattern REGEX = Pattern.compile(".*var jsData\\s*=\\s*\\\"(.*)\\$(.*)\\\";var dateStr=.*");

    @Override
    public List<League> parse(String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Elements elements = Jsoup.parse(data).select("script[type=\"text/javascript\"]");
        for (Element element : elements) {
            String html = element.html();
            if (!html.contains("var jsData")) {
                continue;
            }
            Matcher matcher = REGEX.matcher(html);
            if (matcher.matches() && matcher.groupCount() >= 2) {
                String leagueData = matcher.group(1);
                String matchesData = matcher.group(2);
                Map<Integer, League> leagueMap = parseLeague(leagueData);
                List<Match> matches = parseMatches(matchesData, leagueMap);
                return groupBy(matches);
            }
        }
        return Collections.emptyList();
    }

    private List<League> groupBy(List<Match> matches) {
        List<League> leagueList = new ArrayList<>();
        matches.stream().collect(Collectors.groupingBy(Match::getLeague))
                .forEach((league, matchList) -> {
                    league.setMatchList(matchList);
                    leagueList.add(league);
                });
        return leagueList;
    }

    private Map<Integer, League> parseLeague(String leagueData) {
        Map<Integer, League> map = new HashMap<>();
        String[] strLeagues = leagueData.split("!");
        if (!ObjectUtils.isEmpty(strLeagues)) {
            for (String strLeague : strLeagues) {
                String[] splits = strLeague.split("\\^");
                if (!ObjectUtils.isEmpty(splits) && splits.length >= 3) {
                    String name = splits[0];
                    Integer id = ParserUtils.parseInt(splits[1]);
                    boolean isTopLevel = "1".equals(splits[2]);
                    League league = new League(id, name, isTopLevel);
                    map.put(id, league);
                }
            }
        }
        return map;
    }

    private List<Match> parseMatches(String matchesData, Map<Integer, League> leagueMap) {
        String[] strMatches = matchesData.split("!");
        if (!ObjectUtils.isEmpty(strMatches)) {
            List<Match> matchList = new ArrayList<>();
            for (String strMatch : strMatches) {
                try {
                    Match match = parseMatch(strMatch, leagueMap);
                    matchList.add(match);
                } catch (Exception e) {
                    logger.error(String.format("Parse match error with `%s`", strMatch));
                }
            }
            return matchList;
        }
        return Collections.emptyList();
    }


    private Match parseMatch(String strMatch, Map<Integer, League> leagueMap) {
        String[] splits = strMatch.split("\\^");
        if (!ObjectUtils.isEmpty(splits) && splits.length >= 15) {
            Match match = new Match();
            match.setId(Long.parseLong(splits[0]));
            match.setLeague(leagueMap.get(ParserUtils.parseInt(splits[1])));
            match.setState(MatchState.from(ParserUtils.parseInt(splits[2])));
            match.setMatchTime(DateUtils.parseToUTCTime(splits[3], "yyyyMMddHHmmss"));
            match.setLocalTime(DateUtils.parseToLocalTime((ObjectUtils.isEmpty(splits[4]) ? splits[3] : splits[4]), "yyyyMMddHHmmss"));

            match.setHome(splits[5]);
            match.setAway(splits[6]);

            match.setHomeGoals(ParserUtils.parseInt(splits[7]));
            match.setAwayGoals(ParserUtils.parseInt(splits[8]));

            match.setHomeHalfGoals(ParserUtils.parseInt(splits[9]));
            match.setAwayHalfGoals(ParserUtils.parseInt(splits[10]));

            match.setHomeRedCards(ParserUtils.parseInt(splits[11]));
            match.setAwayRedCards(ParserUtils.parseInt(splits[12]));

            match.setHomeYellowCards(ParserUtils.parseInt(splits[13]));
            match.setAwayYellowCards(ParserUtils.parseInt(splits[14]));

            return match;
        }
        throw new IllegalArgumentException(String.format("Parse match error with string %s", strMatch));
    }
}
