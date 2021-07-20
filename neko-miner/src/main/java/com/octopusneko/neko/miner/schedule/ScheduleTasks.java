package com.octopusneko.neko.miner.schedule;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.parser.IMatchParser;
import com.octopusneko.neko.miner.service.IMatchService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    public ScheduleTasks(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


    @Scheduled(fixedRateString = "${app.schedule.fixedRate}")
    public void updateMatch() {
        LocalDate currDate = LocalDate.now();
        LocalDateTime currDateTenClock = LocalDateTime.of(currDate, LocalTime.of(10, 0));
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.isBefore(currDateTenClock) ? currDate.minusDays(1) : currDate;

        String url = baseUrl.replace("<placeholder>", date.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        String html = responseEntity.getBody();

        if (ObjectUtils.isEmpty(html)) {
            return;
        }

        Jsoup.parse(html).select("script[type=\"text/javascript\"]").forEach(element -> {
            if (element.html().contains("var jsData")) {
                List<League> leagueList = matchParser.parse(element.html());
                leagueList.forEach(league -> matchService.save(league));
            }
        });
    }
}
