package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;

import java.util.List;

public interface IMatchService {

    List<League> saveLeagueMatches(List<League> leagues);

    List<Provider> saveProviders(List<Provider> providers);

    List<Odds> saveOdds(List<Odds> oddsList);

    List<Handicap> saveHandicap(List<Handicap> handicapList);

    List<OverUnder> saveOverUnder(List<OverUnder> overUnderList);
}
