package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.util.List;

public interface IRestService {

    List<League> downloadLeagueMatches(LocalDate date);

    List<Provider> downloadHandicapProviders(final Match match);

    List<Provider> downloadOddsProviders(final Match match);

    List<Provider> downloadOverUnderProviders(final Match match);

    List<Handicap> downloadHandicap(final Provider provider);

    List<Odds> downloadOdds(final Provider provider);

    List<OverUnder> downloadOverUnder(final Provider provider);

    @Retryable(value = SocketTimeoutException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    String getString(String url) throws SocketTimeoutException;
}