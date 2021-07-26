package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OverUnderProviderParserImplTest {

    private IProviderParser providerParser;

    @BeforeEach
    void setup() {
        providerParser = new OverUnderProviderParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);

        String table = "<table id=\"oTable\" style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"mytable3 huibg\"> \n" +
                "    <tbody>\n" +
                "     <tr> \n" +
                "      <th style=\"width:17%;\">公司</th> \n" +
                "      <th style=\"width:33%;\">初指</th> \n" +
                "      <th style=\"width:33%;\">即时</th> \n" +
                "      <th style=\"width:5%;\">变</th> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'1')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">澳门</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.87</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.83</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.80</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'3')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">Crown</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.95</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.81</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.84</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.02</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'8')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">365</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.95</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.83</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.03</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'12')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">易胜博</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.94</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.82</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.09</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'14')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">韦德</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.95</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.83</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.80</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">0.95</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'17')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">明陞</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.88</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.88</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds red\">0.96</span> <span class=\"odds red\">12</span> <span class=\"odds green\">0.84</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'22')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">10B</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.89</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.89</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.81</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">0.99</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'23')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">金宝博</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.96</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.80</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.81</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.07</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'24')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">12B</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.88</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.88</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.84</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.06</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'31')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">利记</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.92</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.90</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.83</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.07</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'35')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">盈禾</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.97</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.82</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.86</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">1.03</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'42')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">18B</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.86</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.86</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.82</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">0.94</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'4')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">立博</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.87</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.83</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.61</span> <span class=\"odds \">2.5</span> <span class=\"odds red\">1.15</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'47')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">平博</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.93</span> <span class=\"odds\">2.25</span> <span class=\"odds\">0.88</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.88</span> <span class=\"odds red\">2.75</span> <span class=\"odds red\">0.98</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'48')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">香港马会</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">0.75</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.95</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.63</span> <span class=\"odds \">2.5</span> <span class=\"odds red\">1.13</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr class=\"alterbg\" onclick=\"showDetail( '2008604' ,'49')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">BWin</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">1.05</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.70</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.65</span> <span class=\"odds \">2.5</span> <span class=\"odds red\">1.10</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "     <tr onclick=\"showDetail( '2008604' ,'19')\"> \n" +
                "      <td style=\"text-align:center;color:#36c\">Interwetten</td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds\">1.10</span> <span class=\"odds\">2.5</span> <span class=\"odds\">0.65</span> </td> \n" +
                "      <td style=\"text-align:center;\"> <span class=\"odds green\">0.65</span> <span class=\"odds \">2.5</span> <span class=\"odds red\">1.10</span> </td> \n" +
                "      <td style=\"text-align:center;\"><img src=\"/Content/images/t.png\" style=\"width:5px;\" height=\"9\"></td> \n" +
                "     </tr> \n" +
                "    </tbody>\n" +
                "   </table> ";
        List<Provider> providers = providerParser.parse(match, table);
        Assertions.assertNotNull(providers);
        Assertions.assertEquals(17, providers.size());
        Assertions.assertEquals(1, providers.get(0).getProviderId().getCode());
        Assertions.assertEquals(19, providers.get(providers.size() - 1).getProviderId().getCode());

    }

}