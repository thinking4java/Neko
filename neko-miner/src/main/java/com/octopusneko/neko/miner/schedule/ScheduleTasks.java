package com.octopusneko.neko.miner.schedule;

import com.octopusneko.neko.miner.listener.event.MatchListEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.service.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduleTasks {

    @Autowired
    @Qualifier("MatchListListener")
    private ApplicationListener<MatchListEvent> applicationListener;

    @Autowired
    private IRestService restService;

    @Autowired
    private JobScheduler jobScheduler;

    @Async("threadPoolTaskScheduler")
    @Scheduled(fixedRateString = "${app.schedule.updateMatchFixedRate:180000}")
    public void updateMatch() {
        LocalDate currDate = LocalDate.now();
        LocalDateTime currDateTenOClock = LocalDateTime.of(currDate, LocalTime.of(10, 0));
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.isBefore(currDateTenOClock) ? currDate.minusDays(1) : currDate;
        List<League> leagues = downloadLeagueMatches(date);
        applicationListener.onApplicationEvent(new MatchListEvent(leagues));
    }

    private List<League> downloadLeagueMatches(LocalDate date) {
        return restService.downloadLeagueMatches(date);
    }

    @Async("threadPoolTaskScheduler")
    @Scheduled(fixedRateString = "${app.schedule.dumpJobFixedRate:300000}")
    public void dumpJob() {
        jobScheduler.dump();
    }
}
