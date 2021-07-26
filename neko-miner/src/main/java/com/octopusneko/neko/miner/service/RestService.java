package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RestService {

    private static final String MATCH_ID_PLACEHOLDER = "<matchId>";
    private static final String PROVIDER_ID_PLACEHOLDER = "<providerId>";

    @Autowired
    private IMatchService matchService;

    @Value("${rest.connectTimeout}")
    private long connectTimeout;

    @Value("${rest.readTimeout}")
    private long readTimeout;

    @Value("${app.match.baseUrl}")
    private String baseUrl;

    @Value("${app.match.matchListPath}")
    private String matchListPath;
    @Autowired
    private IMatchParser matchParser;

    @Autowired
    @Qualifier("HandicapProviderParserImpl")
    private IProviderParser handicapProviderParser;
    @Value("${app.match.handicapPath}")
    private String handicapPath;

    @Autowired
    private IHandicapParser handicapParser;
    @Value("${app.match.handicapDetailPath}")
    private String handicapDetailPath;

    @Autowired
    @Qualifier("OddsProviderParserImpl")
    private IProviderParser oddsProviderParser;
    @Value("${app.match.oddsPath}")
    private String oddsPath;

    @Autowired
    private IOddsParser oddsParser;
    @Value("${app.match.oddsDetailPath}")
    private String oddsDetailPath;

    @Autowired
    @Qualifier("OverUnderProviderParserImpl")
    private IProviderParser overUnderProviderParser;
    @Value("${app.match.overUnderPath}")
    private String overUnderPath;

    @Autowired
    private IOverUnderParser overUnderParser;
    @Value("${app.match.overUnderDetailPath}")
    private String overUnderDetailPath;

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    private String getHtml(String url) {
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
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

    public List<League> downloadLeagueMatches(LocalDate date) {
        String url = String.format("%s%s", baseUrl, matchListPath.replace("<date>", date.toString()));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return matchParser.parse(html);
    }

    public List<Provider> downloadHandicapProviders(final Match match) {
        String url = String.format("%s%s", baseUrl, handicapPath.replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return handicapProviderParser.parse(match, html);
    }

    public List<Provider> downloadOddsProviders(final Match match) {
        String url = String.format("%s%s", baseUrl, oddsPath.replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return oddsProviderParser.parse(match, html);
    }


    public List<Provider> downloadOverUnderProviders(Match match) {
        String url = String.format("%s%s", baseUrl, overUnderPath.replace(MATCH_ID_PLACEHOLDER, String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return overUnderProviderParser.parse(match, html);
    }

    public List<Handicap> downloadHandicap(Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", baseUrl, handicapDetailPath
                .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        Match match = matchService.findMatchById(providerId.getMatchId());
        return handicapParser.parse(match, providerId.getCode(), html);
    }

    public List<Odds> downloadOdds(Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", baseUrl, oddsDetailPath
                .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        Match match = matchService.findMatchById(providerId.getMatchId());
        return oddsParser.parse(match, providerId.getCode(), html);
    }

    public List<OverUnder> downloadOverUnder(Provider provider) {
        Provider.ProviderId providerId = provider.getProviderId();
        String url = String.format("%s%s", baseUrl, overUnderDetailPath
                .replace(MATCH_ID_PLACEHOLDER, String.valueOf(providerId.getMatchId())))
                .replace(PROVIDER_ID_PLACEHOLDER, String.valueOf(providerId.getCode()));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        Match match = matchService.findMatchById(providerId.getMatchId());
        return overUnderParser.parse(match, providerId.getCode(), html);
    }
}
