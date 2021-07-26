package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.listener.event.MatchListEvent;
import com.octopusneko.neko.miner.listener.event.ProviderEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.schedule.Scheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import com.octopusneko.neko.miner.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MatchListListener")
public class MatchListListener implements ApplicationListener<MatchListEvent> {

    @Autowired
    @Qualifier("HandicapListener")
    private ApplicationListener<ProviderEvent> handicapListener;

    @Autowired
    @Qualifier("OddsListener")
    private ApplicationListener<ProviderEvent> oddsListener;

    @Autowired
    @Qualifier("OverUnderListener")
    private ApplicationListener<ProviderEvent> overUnderListener;

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
                scheduler.schedule(() -> downloadHandicapProviders(match));
                scheduler.schedule(() -> downloadOddsProviders(match));
                scheduler.schedule(() -> downloadOverUnderProviders(match));
            });
        });
    }

    private void downloadHandicapProviders(Match match) {
        List<Provider> providers = restService.downloadHandicapProviders(match);
        handicapListener.onApplicationEvent(new ProviderEvent(providers));
    }

    private void downloadOddsProviders(Match match) {
        List<Provider> providers = restService.downloadOddsProviders(match);
        oddsListener.onApplicationEvent(new ProviderEvent(providers));
    }

    private void downloadOverUnderProviders(Match match) {
        List<Provider> providers = restService.downloadOverUnderProviders(match);
        overUnderListener.onApplicationEvent(new ProviderEvent(providers));
    }
}
