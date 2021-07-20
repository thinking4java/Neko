package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.MatchState;
import com.octopusneko.neko.miner.utils.DateUtils;
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

    private static final Pattern REGEX = Pattern.compile(".*var jsData=(.*)\\$(.*);var dateStr=.*");

    @Override
    public List<League> parse(String data) {
        if (!ObjectUtils.isEmpty(data)) {
            Matcher matcher = REGEX.matcher(data);
            if (matcher.matches() && matcher.groupCount() >= 2) {
                String leagueData = matcher.group(1);
                Map<Integer, League> leagueMap = parseLeague(leagueData);
                String matchesData = matcher.group(2);
                List<League> leagueList = new ArrayList<>();
                List<Match> matches = parseMatches(matchesData, leagueMap);
                matches.stream().collect(Collectors.groupingBy(Match::getLeague))
                        .forEach((league, matchList) -> {
                            league.setMatchList(matchList);
                            leagueList.add(league);
                        });
                return leagueList;
            }
        }
        return Collections.emptyList();
    }

    private Map<Integer, League> parseLeague(String leagueData) {
        Map<Integer, League> map = new HashMap<>();
        String[] strLeagues = leagueData.split("!");
        if (!ObjectUtils.isEmpty(strLeagues)) {
            for (String strLeague : strLeagues) {
                String[] splits = strLeague.split("\\^");
                if (!ObjectUtils.isEmpty(splits) && splits.length >= 3) {
                    String name = splits[0];
                    Integer id = parseInt(splits[1]);
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


    private Match parseMatch(String strMatch, Map<Integer, League> leagueMap) throws Exception {
        String[] splits = strMatch.split("\\^");
        if (!ObjectUtils.isEmpty(splits) && splits.length >= 15) {
            Match match = new Match();
            match.setId(Long.parseLong(splits[0]));
            match.setLeague(leagueMap.get(parseInt(splits[1])));
            match.setState(MatchState.from(parseInt(splits[2])));
            match.setMatchTime(DateUtils.parseToUTCTime(splits[3], "yyyyMMddHHmmss"));
            match.setLocalTime(DateUtils.parseToLocalTime((ObjectUtils.isEmpty(splits[4]) ? splits[3] : splits[4]), "yyyyMMddHHmmss"));

            match.setHome(splits[5]);
            match.setAway(splits[6]);

            match.setHomeGoals(parseInt(splits[7]));
            match.setAwayGoals(parseInt(splits[8]));

            match.setHomeHalfGoals(parseInt(splits[9]));
            match.setAwayHalfGoals(parseInt(splits[10]));

            match.setHomeRedCards(parseInt(splits[11]));
            match.setAwayRedCards(parseInt(splits[12]));

            match.setHomeYellowCards(parseInt(splits[13]));
            match.setAwayYellowCards(parseInt(splits[14]));

            return match;
        }
        throw new IllegalArgumentException(String.format("Parse match error with string %s", strMatch));
    }

    private static int parseInt(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

}
