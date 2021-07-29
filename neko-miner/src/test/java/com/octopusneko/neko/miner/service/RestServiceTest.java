package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.parser.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @Mock
    private IMatchParser matchParser;

    @Mock
    private IProviderParser handicapProviderParser;

    @Mock
    private IProviderParser oddsProviderParser;

    @Mock
    private IProviderParser overUnderProviderParser;

    @Mock
    private IHandicapParser handicapParser;

    @Mock
    private IOddsParser oddsParser;

    @Mock
    private IOverUnderParser overUnderParser;

    @Mock
    private IMatchService matchService;

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

    @Test
    void test_downloadHandicapProviders() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 8, 0));
        provider.setName("365");
        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getHandicapPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(handicapProviderParser.parse(match, responseEntity.getBody()))
                .thenReturn(Collections.singletonList(provider));

        List<Provider> providerList = restService.downloadHandicapProviders(match);
        Assertions.assertNotNull(providerList);
        Assertions.assertEquals(1, providerList.size());

        Provider downloadedProvider = providerList.get(0);
        Assertions.assertNotNull(downloadedProvider);
        Assertions.assertEquals(provider, downloadedProvider);
        Assertions.assertEquals(match.getId(), downloadedProvider.getProviderId().getMatchId());
    }

    @Test
    void test_downloadOddsProviders() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 281, 0));
        provider.setName("365");
        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getOddsPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(oddsProviderParser.parse(match, responseEntity.getBody()))
                .thenReturn(Collections.singletonList(provider));

        List<Provider> providerList = restService.downloadOddsProviders(match);
        Assertions.assertNotNull(providerList);
        Assertions.assertEquals(1, providerList.size());

        Provider downloadedProvider = providerList.get(0);
        Assertions.assertNotNull(downloadedProvider);
        Assertions.assertEquals(provider, downloadedProvider);
        Assertions.assertEquals(match.getId(), downloadedProvider.getProviderId().getMatchId());
    }

    @Test
    void test_downloadOverUnderProviders() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 82, 0));
        provider.setName("立博");
        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getOverUnderPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(overUnderProviderParser.parse(match, responseEntity.getBody()))
                .thenReturn(Collections.singletonList(provider));

        List<Provider> providerList = restService.downloadOverUnderProviders(match);
        Assertions.assertNotNull(providerList);
        Assertions.assertEquals(1, providerList.size());

        Provider downloadedProvider = providerList.get(0);
        Assertions.assertNotNull(downloadedProvider);
        Assertions.assertEquals(provider, downloadedProvider);
        Assertions.assertEquals(match.getId(), downloadedProvider.getProviderId().getMatchId());
    }

    @Test
    void test_downloadHandicap() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 8, 0));
        provider.setName("365");

        Handicap handicap = new Handicap();
        handicap.setHandicap(new BigDecimal("-0.5"));
        handicap.setMatch(match);
        handicap.setProviderId(provider.getProviderId().getCode());
        handicap.setId("10000-365-201707291202");
        handicap.setHome(new BigDecimal("0.82"));
        handicap.setAway(new BigDecimal("0.982"));

        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getHandicapDetailPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(matchService.findMatchById(10000L)).thenReturn(match);
        Mockito.when(handicapParser.parse(match, provider.getProviderId().getCode(), responseEntity.getBody()))
                .thenReturn(Collections.singletonList(handicap));

        List<Handicap> handicapList = restService.downloadHandicap(provider);
        Assertions.assertNotNull(handicapList);
        Assertions.assertEquals(1, handicapList.size());

        Handicap downloadedHandicap = handicapList.get(0);
        Assertions.assertNotNull(downloadedHandicap);
        Assertions.assertEquals(handicap, downloadedHandicap);
        Assertions.assertEquals(match.getId(), downloadedHandicap.getMatch().getId());
    }

    @Test
    void test_downloadOdds() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 281, 1));
        provider.setName("365");

        Odds odds = new Odds();
        odds.setMatch(match);
        odds.setProviderId(provider.getProviderId().getCode());
        odds.setId("10000-365-201707291202");
        odds.setHome(new BigDecimal("2.20"));
        odds.setDraw(new BigDecimal("3.45"));
        odds.setAway(new BigDecimal("4.10"));

        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getOddsDetailPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(matchService.findMatchById(10000L)).thenReturn(match);
        Mockito.when(oddsParser.parse(match, provider.getProviderId().getCode(), responseEntity.getBody()))
                .thenReturn(Collections.singletonList(odds));

        List<Odds> oddsList = restService.downloadOdds(provider);
        Assertions.assertNotNull(oddsList);
        Assertions.assertEquals(1, oddsList.size());

        Odds downloadedOdds = oddsList.get(0);
        Assertions.assertNotNull(downloadedOdds);
        Assertions.assertEquals(odds, downloadedOdds);
        Assertions.assertEquals(match.getId(), downloadedOdds.getMatch().getId());
    }

    @Test
    void test_downloadOverUnder() {
        League league = new League(33, "PrimeLeague", true);
        Match match = new Match(10000L, league, MatchState.FINISHED);
        Provider provider = new Provider();
        provider.setProviderId(new Provider.ProviderId(match.getId(), 8, 2));
        provider.setName("365");

        OverUnder overUnder = new OverUnder();
        overUnder.setMatch(match);
        overUnder.setProviderId(provider.getProviderId().getCode());
        overUnder.setId("10000-365-201707291202");
        overUnder.setHome(new BigDecimal("0.75"));
        overUnder.setOverUnder(new BigDecimal("2.25"));
        overUnder.setAway(new BigDecimal("0.95"));

        Mockito.when(matchConfig.getBaseUrl()).thenReturn("");
        Mockito.when(matchConfig.getOverUnderDetailPath()).thenReturn("");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange("", HttpMethod.GET,
                new HttpEntity<>(null, fakeHttpHeaders()), String.class))
                .thenReturn(responseEntity);
        Mockito.when(matchService.findMatchById(10000L)).thenReturn(match);
        Mockito.when(overUnderParser.parse(match, provider.getProviderId().getCode(), responseEntity.getBody()))
                .thenReturn(Collections.singletonList(overUnder));

        List<OverUnder> overUnderList = restService.downloadOverUnder(provider);
        Assertions.assertNotNull(overUnderList);
        Assertions.assertEquals(1, overUnderList.size());

        OverUnder downloadedOverUnder = overUnderList.get(0);
        Assertions.assertNotNull(downloadedOverUnder);
        Assertions.assertEquals(overUnder, downloadedOverUnder);
        Assertions.assertEquals(match.getId(), downloadedOverUnder.getMatch().getId());
    }
}