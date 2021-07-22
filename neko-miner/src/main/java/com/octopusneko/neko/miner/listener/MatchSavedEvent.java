package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.model.League;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class MatchSavedEvent extends ApplicationEvent {

    public MatchSavedEvent(List<League> savedLeagues) {
        super(savedLeagues);
    }
}
