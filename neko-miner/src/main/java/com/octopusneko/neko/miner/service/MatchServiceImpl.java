package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;
import com.octopusneko.neko.miner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private OddsRepository oddsRepository;

    @Autowired
    private HandicapRepository handicapRepository;

    @Autowired
    private OverUnderRepository overUnderRepository;

    @Override
    public List<League> saveLeagueMatches(List<League> league) {
        return leagueRepository.saveAll(league);
    }

    @Override
    public List<Provider> saveProviders(List<Provider> providers) {
        return providerRepository.saveAll(providers);
    }

    @Override
    public List<Odds> saveOdds(List<Odds> oddsList) {
        return oddsRepository.saveAll(oddsList);
    }

    @Override
    public List<Handicap> saveHandicap(List<Handicap> handicapList) {
        return handicapRepository.saveAll(handicapList);
    }

    @Override
    public List<OverUnder> saveOverUnder(List<OverUnder> overUnderList) {
        return overUnderRepository.saveAll(overUnderList);
    }
}
