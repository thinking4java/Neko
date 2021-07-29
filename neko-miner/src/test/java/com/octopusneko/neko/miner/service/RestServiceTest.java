package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.MatchState;
import com.octopusneko.neko.miner.parser.IMatchParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @Mock
    private IMatchParser matchParser;

    @Mock
    private MatchConfig matchConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestService restService;

    private HttpHeaders fakeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // fake browser's behavior
        headers.add("authority", "m.nowscore.com");
        headers.add("cache-control", "max-age=0");
        headers.add("sec-ch-ua", "\" Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"91\", \"Chromium\";v=\"91\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("upgrade-insecure-requests", "1");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        headers.add("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("sec-fetch-site", "none");
        headers.add("sec-fetch-mode", "navigate");
        headers.add("sec-fetch-user", "?1");
        headers.add("sec-fetch-dest", "document");
        headers.add("accept-language", "en-US,en;q=0.9");
        return headers;
    }

    @Test
    void test_downloadLeagueMatches() {
        League league = new League(33, "PrimeLeague", true);
        league.setMatchList(Collections.singletonList(new Match(10000L, league, MatchState.FINISHED)));
        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getMatchListPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(matchParser.parse(responseEntity.getBody()))
                .thenReturn(Collections.singletonList(league));

        List<League> leagueList = restService.downloadLeagueMatches(LocalDate.now());
        Assertions.assertNotNull(leagueList);
        Assertions.assertEquals(1, leagueList.size());

        League downloadedLeague = leagueList.get(0);
        Assertions.assertTrue(downloadedLeague.isTopLevel());
        Assertions.assertNotNull(downloadedLeague.getMatchList());
        Assertions.assertEquals(1, downloadedLeague.getMatchList().size());
        Assertions.assertEquals(10000L, downloadedLeague.getMatchList().get(0).getId());
        Assertions.assertEquals(MatchState.FINISHED, downloadedLeague.getMatchList().get(0).getState());
    }
}