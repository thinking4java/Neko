package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.job.HandicapProviderJob;
import com.octopusneko.neko.miner.job.OddsProviderJob;
import com.octopusneko.neko.miner.job.OverUnderProviderJob;
import com.octopusneko.neko.miner.listener.event.MatchListEvent;
import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.job.Job;
import com.octopusneko.neko.miner.schedule.JobScheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("MatchListListener")
public class MatchListListener implements ApplicationListener<MatchListEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private IMatchService matchService;

    @Override
    public void onApplicationEvent(MatchListEvent event) {
        List<League> leagues = event.getMatchList();
        List<League> savedLeagueMatches = matchService.saveLeagueMatches(leagues);
        savedLeagueMatches.forEach(league -> {
            List<Match> matchList = league.getMatchList();
            matchList.forEach(match -> Arrays.asList(createJob(HandicapProviderJob.class, match)
                    , createJob(OddsProviderJob.class, match)
                    , createJob(OverUnderProviderJob.class, match))
                    .forEach(job -> jobScheduler.schedule(job)));
        });
    }

    private Job createJob(Class<? extends Job> clazz, Match match) {
        return applicationContext.getBean(clazz, match);
    }
}
