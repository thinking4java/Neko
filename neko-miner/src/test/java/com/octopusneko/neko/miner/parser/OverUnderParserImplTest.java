package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.OverUnder;
import com.octopusneko.neko.miner.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OverUnderParserImplTest {

    private IOverUnderParser overUnderParser;

    @BeforeEach
    void setUp() {
        overUnderParser = new OverUnderParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);
        match.setMatchTime(DateUtils.parseToUTCTime("2021-07-22 15:30", "yyyy-MM-dd HH:mm"));
        String table = "<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"hTable\"> \n" +
                "         <tbody>\n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.98</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.88</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 20:51</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.00</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.85</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 20:48</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.98</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.88</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 19:02</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.00</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.85</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 17:53</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 14:32</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 14:31</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 14:11</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 14:08</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 14:06</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 13:30</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 13:14</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 13:13</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 13:12</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 13:02</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 11:29</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 10:39</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 10:10</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 05:56</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.00</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.85</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 05:36</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-26 01:56</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.10</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.78</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-25 19:31</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-25 01:10</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.83</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.03</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-24 22:00</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-24 14:01</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-24 02:17</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.05</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.80</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-24 02:01</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.03</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.83</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-20 17:39</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">0.85</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">1.00</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-20 04:49</td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"green\">0.83</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"red\">1.03</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-20 04:30</td> \n" +
                "          </tr> \n" +
                "          <tr class=\"alterbg\"> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">0.85</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">2/2.5</td> \n" +
                "           <td style=\"width:22%;text-align:center;\" class=\"\">1.00</td> \n" +
                "           <td style=\"width:34%;text-align:center;padding-right:2px;\">07-18 04:48</td> \n" +
                "          </tr> \n" +
                "         </tbody>\n" +
                "        </table> \n" +
                "       </div> </td> \n" +
                "     </tr> \n" +
                "    </tbody>\n" +
                "   </table> ";
        List<OverUnder> overUnderList = overUnderParser.parse(match, 8, table);
        Assertions.assertNotNull(overUnderList);
        Assertions.assertEquals(30, overUnderList.size());
        Assertions.assertEquals(2.5f, overUnderList.get(0).getOverUnder());
        Assertions.assertEquals(2.25f, overUnderList.get(overUnderList.size() - 1).getOverUnder());
    }
}