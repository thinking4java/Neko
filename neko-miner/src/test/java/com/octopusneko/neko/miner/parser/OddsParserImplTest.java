package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Odds;
import com.octopusneko.neko.miner.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OddsParserImplTest {

    private IOddsParser oddsParser;

    @BeforeEach
    void setUp() {
        oddsParser = new OddsParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);
        match.setMatchTime(DateUtils.parseToUTCTime("2021-07-22 15:30", "yyyy-MM-dd HH:mm"));
        String table = "<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mytable2\"> \n" +
                "    <tbody>\n" +
                "     <tr> \n" +
                "      <th style=\"width:40%;text-align:center;\" colspan=\"3\">365数据变化</th> \n" +
                "      <th style=\"width:15%;text-align:center;\" class=\"lrline\">返还率</th> \n" +
                "      <th style=\"width:30%;text-align:center;\">凯利指数</th> \n" +
                "      <th style=\"width:15%;text-align:center;\">&nbsp;</th> \n" +
                "     </tr> \n" +
                "     <tr> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">2.10</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green lrline\">93.94</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.95</span> <span class=\"odds \">0.95</span> <span class=\"odds \">0.92</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-26\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        15:24\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\"> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">3.30</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">2.15</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red lrline\">94.15</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.92</span> <span class=\"odds \">0.95</span> <span class=\"odds \">0.95</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-26\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        13:57\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">2.10</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green lrline\">93.94</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.95</span> <span class=\"odds \">0.95</span> <span class=\"odds \">0.92</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-25\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        19:31\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\"> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.30</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">2.15</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red lrline\">94.15</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.92</span> <span class=\"odds \">0.95</span> <span class=\"odds \">0.95</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-25\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        01:10\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">3.30</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.50</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">2.10</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red lrline\">93.92</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.92</span> <span class=\"odds \">0.98</span> <span class=\"odds \">0.92</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-24\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        22:00\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\"> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">3.40</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.50</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">2.05</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red lrline\">93.67</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.95</span> <span class=\"odds \">0.98</span> <span class=\"odds \">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-23\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        22:57\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.60</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">3.50</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">1.95</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green lrline\">92.92</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds red\">1.01</span> <span class=\"odds \">0.98</span> <span class=\"odds \">0.86</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-23\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        19:00\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\"> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">3.60</td> \n" +
                "      <td style=\"text-align:center;\" class=\"red\">3.25</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green\">2.10</td> \n" +
                "      <td style=\"text-align:center;\" class=\"green lrline\">94.18</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds red\">1.01</span> <span class=\"odds \">0.91</span> <span class=\"odds \">0.92</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-20\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        16:03\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "     <tr> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.30</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">3.20</td> \n" +
                "      <td style=\"text-align:center;\" class=\"\">2.25</td> \n" +
                "      <td style=\"text-align:center;\" class=\" lrline\">94.35</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds \">0.92</span> <span class=\"odds \">0.90</span> <span class=\"odds \">0.99</span> </td> \n" +
                "      <td style=\"text-align:center;line-height:normal;\"> \n" +
                "       <div>\n" +
                "        07-18\n" +
                "       </div> \n" +
                "       <div>\n" +
                "        04:46\n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "    </tbody>\n" +
                "   </table> ";
        List<Odds> oddsList = oddsParser.parse(match, 8, table);
        Assertions.assertNotNull(oddsList);
        Assertions.assertEquals(9, oddsList.size());
        Assertions.assertEquals(3.40f, oddsList.get(0).getDraw().floatValue());
        Assertions.assertEquals(3.20f, oddsList.get(oddsList.size() - 1).getDraw().floatValue());
    }
}