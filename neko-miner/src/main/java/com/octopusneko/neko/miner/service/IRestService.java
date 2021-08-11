package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.util.List;

public interface IRestService {

    List<League> downloadLeagueMatches(LocalDate date);

    List<Provider> downloadHandicapProviders(final Match match);

    List<Provider> downloadOddsProviders(final Match match);

    List<Provider> downloadOverUnderProviders(final Match match);

    List<Handicap> downloadHandicap(final Match match, final Provider provider);

    List<Odds> downloadOdds(final Match match, final Provider provider);

    List<OverUnder> downloadOverUnder(final Match match, final Provider provider);

    String getString(String url) throws SocketTimeoutException;
}
