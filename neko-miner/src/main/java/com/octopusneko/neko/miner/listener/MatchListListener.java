package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.listener.event.HandicapEvent;
import com.octopusneko.neko.miner.listener.event.MatchListEvent;
import com.octopusneko.neko.miner.listener.event.OddsEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.schedule.Scheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import com.octopusneko.neko.miner.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchListListener implements ApplicationListener<MatchListEvent> {

    @Autowired
    private ApplicationListener<HandicapEvent> handicapListener;

    @Autowired
    private ApplicationListener<OddsEvent> oddsListener;

    @Autowired
    private RestService restService;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private IMatchService matchService;

    @Override
    public void onApplicationEvent(MatchListEvent event) {
        List<League> leagues = event.getMatchList();
        List<League> savedLeagueMatches = matchService.saveLeagueMatches(leagues);
        savedLeagueMatches.forEach(league -> {
            List<Match> matchList = league.getMatchList();
            matchList.forEach(match -> {
                scheduler.schedule(() -> downloadAsianProviders(match));
                scheduler.schedule(() -> downloadEuroProviders(match));
            });
        });
    }

    private void downloadAsianProviders(Match match) {
        List<Provider> providers = restService.downloadAsianProviders(match);
        handicapListener.onApplicationEvent(new HandicapEvent(providers));
    }

    private void downloadEuroProviders(Match match) {
        List<Provider> providers = restService.downloadEuroProviders(match);
        oddsListener.onApplicationEvent(new OddsEvent(providers));
    }
}
