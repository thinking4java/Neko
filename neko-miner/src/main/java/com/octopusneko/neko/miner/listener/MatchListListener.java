package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.job.HandicapProviderJob;
import com.octopusneko.neko.miner.job.Job;
import com.octopusneko.neko.miner.job.OddsProviderJob;
import com.octopusneko.neko.miner.job.OverUnderProviderJob;
import com.octopusneko.neko.miner.listener.event.MatchListEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.payload.Entry;
import com.octopusneko.neko.miner.schedule.JobScheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("MatchListListener")
public class MatchListListener implements ApplicationListener<MatchListEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private MatchConfig matchConfig;

    @Override
    public void onApplicationEvent(MatchListEvent event) {
        List<League> filteredLeagues = getFilteredLeagues(event.getMatchList());
        List<League> savedLeagueMatches = matchService.saveLeagueMatches(filteredLeagues);
        savedLeagueMatches.forEach(league -> {
            List<Match> matchList = league.getMatchList();
            matchList.forEach(match -> Arrays.asList(createJob(HandicapProviderJob.class, match)
                    , createJob(OddsProviderJob.class, match)
                    , createJob(OverUnderProviderJob.class, match))
                    .forEach(job -> jobScheduler.schedule(job)));
        });
    }

    private List<League> getFilteredLeagues(List<League> leagues) {
        List<Integer> hotLeagueIds = matchConfig.getHotLeagues().stream()
                .map(Entry::getId)
                .collect(Collectors.toList());
        return leagues.stream()
                .filter(league -> hotLeagueIds.contains(league.getId()))
                .collect(Collectors.toList());
    }

    private Job createJob(Class<? extends Job> clazz, Match match) {
        return applicationContext.getBean(clazz, match);
    }
}
