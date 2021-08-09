package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestServiceImpl implements IRestService {

    private static final String MATCH_ID_PLACEHOLDER = "<matchId>";
    private static final String PROVIDER_ID_PLACEHOLDER = "<providerId>";

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IMatchParser matchParser;

    @Autowired
    @Qualifier("HandicapProviderParserImpl")
    private IProviderParser handicapProviderParser;
    @Autowired
    @Qualifier("OddsProviderParserImpl")
    private IProviderParser oddsProviderParser;
    @Autowired
    @Qualifier("OverUnderProviderParserImpl")
    private IProviderParser overUnderProviderParser;

    @Autowired
    private IHandicapParser handicapParser;
    @Autowired
    private IOddsParser oddsParser;
    @Autowired
    private IOverUnderParser overUnderParser;

    @Autowired
    private MatchConfig matchConfig;

    @Autowired
    RestTemplate restTemplate;

    public String getString(String url) {
        HttpHeaders headers = fakeHttpHeaders();
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

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

    public List<League> downloadLeagueMatches(LocalDate date) {
        String url = String.format("%s%s", matchConfig.getBaseUrl(),
                matchConfig.getMatchListPath().replace("<date>", date.toString()));
        String html = getString(url);
        return matchParser.parse(html);
    }

    public List<Provider> downloadHandicapProviders(final Match match) {
        String url = String.format("%s%s", matchConfig.getBaseUrl(),
                matchConfig.getHandicapPath().replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getString(url);
        return handicapProviderParser.parse(match, html);
    }

    public List<Provider> downloadOddsProviders(final Match match) {
        String url = String.format("%s%s", matchConfig.getBaseUrl(),
                matchConfig.getOddsPath().replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getString(url);
        return oddsProviderParser.parse(match, html);
    }


    public List<Provider> downloadOverUnderProviders(final Match match) {
        String url = String.format("%s%s", matchConfig.getBaseUrl(),
                matchConfig.getOverUnderPath().replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getString(url);
        return overUnderProviderParser.parse(match, html);
    }

    public List<Handicap> downloadHandicap(final Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", matchConfig.getBaseUrl()
                , matchConfig.getHandicapDetailPath()
                        .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getString(url);
        Match match = matchService.findMatchById(providerId.getMatchId());
        return handicapParser.parse(match, providerId.getCode(), html);
    }

    public List<Odds> downloadOdds(final Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", matchConfig.getBaseUrl()
                , matchConfig.getOddsDetailPath()
                        .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getString(url);
        Match match = matchService.findMatchById(providerId.getMatchId());
        return oddsParser.parse(match, providerId.getCode(), html);
    }

    public List<OverUnder> downloadOverUnder(final Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", matchConfig.getBaseUrl()
                , matchConfig.getOverUnderDetailPath()
                        .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getString(url);
        Match match = matchService.findMatchById(providerId.getMatchId());
        return overUnderParser.parse(match, providerId.getCode(), html);
    }
}
