package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchSavedListener implements ApplicationListener<MatchSavedEvent> {

    @Override
    public void onApplicationEvent(MatchSavedEvent event) {
        List<League> savedLeagues = (List<League>) event.getSource();
        savedLeagues.forEach(league -> {
            List<Match> matchList = league.getMatchList();
            matchList.forEach(match -> {
                // TODO
            });
        });
    }
}
