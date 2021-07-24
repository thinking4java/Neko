package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.parser.IMatchParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<League> downloadLeagueMatches(LocalDate date) {
        String url = String.format("%s%s", baseUrl, matchListPath.replace("<date>", date.toString()));
        HttpHeaders headers = new HttpHeaders();
        // fake browser's behavior
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String html = response.getBody();
        if (ObjectUtils.isEmpty(html)) {
            return Collections.emptyList();
        }
        return matchParser.parse(html);
    }

    public List<Provider> downloadAsianProviders(Match match) {
        // TODO
        return Collections.emptyList();
    }

    public List<Provider> downloadEuroProviders(Match match) {
        // TODO
        return Collections.emptyList();
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
