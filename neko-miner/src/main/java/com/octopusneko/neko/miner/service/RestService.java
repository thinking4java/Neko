package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.parser.IMatchParser;
import com.octopusneko.neko.miner.parser.IProviderParser;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RestService {

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
    @Qualifier("OddsProviderParserImpl")
    private IProviderParser oddsProviderParser;
    @Value("${app.match.oddsPath}")
    private String oddsPath;

    @Autowired
    @Qualifier("OverUnderProviderParserImpl")
    private IProviderParser overUnderProviderParser;
    @Value("${app.match.overUnderPath}")
    private String overUnderPath;

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    private String getHtml(String url) {
        HttpHeaders headers = new HttpHeaders();
        // fake browser's behavior
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
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

    public List<Provider> downloadHandicapProviders(Match match) {
        String url = String.format("%s%s", baseUrl, handicapPath.replace("<matchId>", String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return handicapProviderParser.parse(match, html);
    }

    public List<Provider> downloadOddsProviders(Match match) {
        String url = String.format("%s%s", baseUrl, oddsPath.replace("<matchId>", String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return oddsProviderParser.parse(match, html);
    }


    public List<Provider> downloadOverUnderProviders(Match match) {
        String url = String.format("%s%s", baseUrl, overUnderPath.replace("<matchId>", String.valueOf(match.getId())));
        String html = getHtml(url);
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return overUnderProviderParser.parse(match, html);
    }


    public List<Odds> downloadOdds(Provider provider) {
        // TODO
        return Collections.emptyList();
    }

    public List<Handicap> downloadHandicap(Provider provider) {
        // TOTO
        return Collections.emptyList();
    }

    public List<OverUnder> downloadOverUnder(Provider provider) {
        // TODO
        return Collections.emptyList();
    }
}
