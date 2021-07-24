package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Handicap;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandicapParserImplTest {

    private IHandicapParser handicapParser;

    @BeforeEach
    void setup() {
        handicapParser = new HandicapParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);
        match.setMatchTime(DateUtils.parseToUTCTime("2021-07-22 15:30", "yyyy-MM-dd HH:mm"));
        String table = "                      <table style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"hTable\">\n" +
                "                                <tr>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"green\">0.95</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"\">-1.5/2</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"red\">0.90</td>\n" +
                "                                    <td style=\"width: 34%; text-align: center; padding-right: 2px;\">07-22 15:20</td>\n" +
                "                                </tr>\n" +
                "\n" +
                "                                <tr class=\"alterbg\">\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"green\">0.98</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"\">-1.5/2</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"red\">0.88</td>\n" +
                "                                    <td style=\"width: 34%; text-align: center; padding-right: 2px;\">07-22 15:08</td>\n" +
                "                                </tr>\n" +
                "\n" +
                "                                <tr class=\"alterbg\">\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"\">1.\t</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"\">-0.5/1</td>\n" +
                "                                    <td style=\"width: 22%; text-align: center;\" class=\"\">0.83</td>\n" +
                "                                    <td style=\"width: 34%; text-align: center; padding-right: 2px;\">07-07 19:54</td>\n" +
                "                                </tr>\n" +
                "                            </table>";
        List<Handicap> handicaps = handicapParser.parse(match, 8, table);
        Assertions.assertNotNull(handicaps);
        Assertions.assertEquals(3, handicaps.size());
        Assertions.assertEquals(-0.75f, handicaps.get(handicaps.size() - 1).getHandicap());
    }

}