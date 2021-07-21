package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public League save(League league) {
        return leagueRepository.save(league);
    }
}
