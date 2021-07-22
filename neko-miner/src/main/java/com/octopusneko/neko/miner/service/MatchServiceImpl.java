package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public List<League> saveAll(List<League> league) {
        return leagueRepository.saveAll(league);
    }
}
