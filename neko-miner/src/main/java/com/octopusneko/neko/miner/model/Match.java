package com.octopusneko.neko.miner.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
public class Match {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;

    @Enumerated(EnumType.STRING)
    private MatchState state;

    // UTC date time
    private ZonedDateTime matchTime;

    // LOCAL date time
    private LocalDateTime localTime;

    private String home;
    private String away;

    private boolean lineupAnnounced;

    private int homeGoals;
    private int awayGoals;

    private int homeHalfGoals;
    private int awayHalfGoals;

    private int homeYellowCards;
    private int awayYellowCards;

    private int homeRedCards;
    private int awayRedCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public ZonedDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(ZonedDateTime matchTime) {
        this.matchTime = matchTime;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public boolean isLineupAnnounced() {
        return lineupAnnounced;
    }

    public void setLineupAnnounced(boolean lineupAnnounced) {
        this.lineupAnnounced = lineupAnnounced;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getHomeHalfGoals() {
        return homeHalfGoals;
    }

    public void setHomeHalfGoals(int homeHalfGoals) {
        this.homeHalfGoals = homeHalfGoals;
    }

    public int getAwayHalfGoals() {
        return awayHalfGoals;
    }

    public void setAwayHalfGoals(int awayHalfGoals) {
        this.awayHalfGoals = awayHalfGoals;
    }

    public int getHomeYellowCards() {
        return homeYellowCards;
    }

    public void setHomeYellowCards(int homeYellowCards) {
        this.homeYellowCards = homeYellowCards;
    }

    public int getAwayYellowCards() {
        return awayYellowCards;
    }

    public void setAwayYellowCards(int awayYellowCards) {
        this.awayYellowCards = awayYellowCards;
    }

    public int getHomeRedCards() {
        return homeRedCards;
    }

    public void setHomeRedCards(int homeRedCards) {
        this.homeRedCards = homeRedCards;
    }

    public int getAwayRedCards() {
        return awayRedCards;
    }

    public void setAwayRedCards(int awayRedCards) {
        this.awayRedCards = awayRedCards;
    }
}
