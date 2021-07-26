package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandicapProviderParserImplTest {

    private IProviderParser providerParser;

    @BeforeEach
    void setup() {
        providerParser = new HandicapProviderParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);

        String table = "<table id=\"hTable\" style=\"width:100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"mytable3 huibg\"> \n" +
                "    <tbody>\n" +
                "     <tr> \n" +
                "      <th style=\"width:17%\">公司</th> \n" +
                "      <th style=\"width:33%\">初指</th> \n" +
                "      <th style=\"width:33%\">即时</th> \n" +
                "      <th style=\"width:5%\">变</th> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'1')\"> \n" +
                "      <td style=\"text-align:center;\"><span style=\"color:#36C;\">澳门</span></td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.96</span> <span class=\"odds\">-1.5</span> <span class=\"odds\">0.84</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.94</span> <span class=\"odds green\">-1.5/2</span> <span class=\"odds red\">0.86</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'3')\"> \n" +
                "      <td style=\"text-align:center;\"><span style=\"color:#36C;\">Crown</span></td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.86</span> <span class=\"odds\">-1/1.5</span> <span class=\"odds\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds red\">0.92</span> <span class=\"odds green\">-1.5/2</span> <span class=\"odds red\">0.96</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'8')\"> \n" +
                "      <td style=\"text-align:center;\"><span style=\"color:#36C;\">365</span></td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">1.03</span> <span class=\"odds\">-0.5/1</span> <span class=\"odds\">0.83</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.95</span> <span class=\"odds green\">-1.5/2</span> <span class=\"odds red\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "    </tbody>\n" +
                "   </table> ";
        List<Provider> providers = providerParser.parse(match, table);
        Assertions.assertNotNull(providers);
        Assertions.assertEquals(3, providers.size());
        Assertions.assertEquals(1, providers.get(0).getProviderId().getCode());

    }

}