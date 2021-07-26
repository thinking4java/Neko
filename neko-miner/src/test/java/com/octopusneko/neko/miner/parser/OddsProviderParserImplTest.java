package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OddsProviderParserImplTest {

    private IProviderParser providerParser;

    @BeforeEach
    void setup() {
        providerParser = new OddsProviderParserImpl();
    }

    @Test
    void testParse() {
        Match match = new Match();
        match.setId(2008604L);
        String data = "<script type=\"text/javascript\">var hData = [{\"cn\":\"十方国际\",\"cId\":16,\"hw\":11.0,\"so\":4.9,\"gw\":1.25,\"rh\":14.0,\"rs\":7.0,\"rg\":1.18,\"ct\":1},{\"cn\":\"12B.com\",\"cId\":18,\"hw\":5.2,\"so\":3.65,\"gw\":1.53,\"rh\":11.0,\"rs\":6.2,\"rg\":1.21,\"ct\":1},{\"cn\":\"18B\",\"cId\":976,\"hw\":5.0,\"so\":3.5,\"gw\":1.63,\"rh\":15.0,\"rs\":6.25,\"rg\":1.18,\"ct\":1},{\"cn\":\"365\",\"cId\":281,\"hw\":5.0,\"so\":3.4,\"gw\":1.62,\"rh\":17.0,\"rs\":5.75,\"rg\":1.2,\"ct\":1},{\"cn\":\"博弈世家\",\"cId\":173,\"hw\":7.4,\"so\":4.0,\"gw\":1.38,\"rh\":14.0,\"rs\":6.0,\"rg\":1.21,\"ct\":1},{\"cn\":\"必发\",\"cId\":2,\"hw\":12.0,\"so\":5.0,\"gw\":1.2,\"rh\":21.0,\"rs\":7.4,\"rg\":1.21,\"ct\":2},{\"cn\":\"Bwin\",\"cId\":255,\"hw\":6.5,\"so\":3.7,\"gw\":1.53,\"rh\":12.0,\"rs\":5.5,\"rg\":1.18,\"ct\":1},{\"cn\":\"Coral\",\"cId\":88,\"hw\":15.0,\"so\":5.2,\"gw\":1.28,\"rh\":19.0,\"rs\":6.5,\"rg\":1.2,\"ct\":1},{\"cn\":\"Crown\",\"cId\":545,\"hw\":6.9,\"so\":4.25,\"gw\":1.35,\"rh\":13.0,\"rs\":5.8,\"rg\":1.2,\"ct\":1},{\"cn\":\"Expekt.com\",\"cId\":70,\"hw\":11.5,\"so\":4.98,\"gw\":1.25,\"rh\":13.75,\"rs\":5.95,\"rg\":1.19,\"ct\":1},{\"cn\":\"Interwetten\",\"cId\":104,\"hw\":5.75,\"so\":3.75,\"gw\":1.57,\"rh\":13.0,\"rs\":6.25,\"rg\":1.22,\"ct\":1},{\"cn\":\"Matchbook\",\"cId\":352,\"hw\":14.5,\"so\":5.1,\"gw\":1.26,\"rh\":17.5,\"rs\":6.8,\"rg\":1.2,\"ct\":2},{\"cn\":\"Nordicbet\",\"cId\":4,\"hw\":15.0,\"so\":4.75,\"gw\":1.28,\"rh\":15.0,\"rs\":6.0,\"rg\":1.2,\"ct\":1},{\"cn\":\"平博\",\"cId\":177,\"hw\":6.85,\"so\":3.52,\"gw\":1.6,\"rh\":16.0,\"rs\":7.0,\"rg\":1.2,\"ct\":1},{\"cn\":\"赛弈\",\"cId\":110,\"hw\":13.0,\"so\":5.0,\"gw\":1.27,\"rh\":16.0,\"rs\":6.25,\"rg\":1.18,\"ct\":1},{\"cn\":\"澳门\",\"cId\":80,\"hw\":9.6,\"so\":4.95,\"gw\":1.23,\"rh\":12.0,\"rs\":5.75,\"rg\":1.15,\"ct\":1},{\"cn\":\"博天堂\",\"cId\":422,\"hw\":7.25,\"so\":3.8,\"gw\":1.48,\"rh\":15.0,\"rs\":6.25,\"rg\":1.17,\"ct\":1},{\"cn\":\"188B\",\"cId\":499,\"hw\":6.9,\"so\":4.25,\"gw\":1.35,\"rh\":13.0,\"rs\":5.8,\"rg\":1.2,\"ct\":1},{\"cn\":\"Lottery Official\",\"cId\":1129,\"hw\":12.5,\"so\":5.1,\"gw\":1.16,\"rh\":15.5,\"rs\":5.75,\"rg\":1.11,\"ct\":1},{\"cn\":\"立博\",\"cId\":82,\"hw\":15.0,\"so\":5.2,\"gw\":1.28,\"rh\":17.0,\"rs\":6.0,\"rg\":1.22,\"ct\":1},{\"cn\":\"Sbobet\",\"cId\":474,\"hw\":4.9,\"so\":3.75,\"gw\":1.54,\"rh\":11.0,\"rs\":6.8,\"rg\":1.21,\"ct\":1},{\"cn\":\"mansion.com\",\"cId\":517,\"hw\":5.2,\"so\":3.65,\"gw\":1.53,\"rh\":11.0,\"rs\":6.2,\"rg\":1.21,\"ct\":1},{\"cn\":\"威廉希尔\",\"cId\":115,\"hw\":8.5,\"so\":3.9,\"gw\":1.4,\"rh\":13.0,\"rs\":5.8,\"rg\":1.2,\"ct\":1},{\"cn\":\"伟德\",\"cId\":81,\"hw\":15.0,\"so\":4.75,\"gw\":1.29,\"rh\":17.0,\"rs\":6.0,\"rg\":1.2,\"ct\":1},{\"cn\":\"香港马会\",\"cId\":432,\"hw\":12.0,\"so\":5.05,\"gw\":1.18,\"rh\":13.0,\"rs\":5.3,\"rg\":1.16,\"ct\":1},{\"cn\":\"易胜博\",\"cId\":90,\"hw\":10.0,\"so\":5.0,\"gw\":1.22,\"rh\":13.0,\"rs\":5.8,\"rg\":1.2,\"ct\":1},{\"cn\":\"Wewbet\",\"cId\":659,\"hw\":6.9,\"so\":4.25,\"gw\":1.35,\"rh\":11.5,\"rs\":5.7,\"rg\":1.22,\"ct\":1}]; ";
        List<Provider> providers = providerParser.parse(match, data);
        Assertions.assertNotNull(providers);
        Assertions.assertEquals(27, providers.size());
        Assertions.assertEquals(16, providers.get(0).getProviderId().getCode());
        Assertions.assertEquals(659, providers.get(providers.size() - 1).getProviderId().getCode());
    }

}