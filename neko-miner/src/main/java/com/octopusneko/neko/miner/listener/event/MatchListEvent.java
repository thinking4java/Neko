package com.octopusneko.neko.miner.listener.event;

import com.octopusneko.neko.miner.model.League;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class MatchListEvent extends ApplicationEvent {

    public MatchListEvent(List<League> leagues) {
        super(leagues);
    }

    public final List<League> getMatchList() {
        return (List<League>) getSource();
    }
}
