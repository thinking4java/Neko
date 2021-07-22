package com.octopusneko.neko.miner.schedule;

import com.octopusneko.neko.miner.listener.MatchSavedEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.parser.IMatchParser;
import com.octopusneko.neko.miner.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Component
public class ScheduleTasks {

    @Value("${app.schedule.match.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    private IMatchParser matchParser;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private ApplicationListener<MatchSavedEvent> applicationListener;

    public ScheduleTasks(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Scheduled(fixedRateString = "${app.schedule.fixedRate}")
    public void updateMatch() {
        LocalDate currDate = LocalDate.now();
        LocalDateTime currDateTenOClock = LocalDateTime.of(currDate, LocalTime.of(10, 0));
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.isBefore(currDateTenOClock) ? currDate.minusDays(1) : currDate;
        List<League> leagues = downloadLeagueMatches(date);
        List<League> savedLeagues = matchService.saveAll(leagues);
        applicationListener.onApplicationEvent(new MatchSavedEvent(savedLeagues));
    }

    private List<League> downloadLeagueMatches(LocalDate date) {
        String url = baseUrl.replace("<placeholder>", date.toString());
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
}
