package com.octopusneko.neko.miner.model;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class OverUnder {

    /**
     * id = {matchId}-{providerId}-{updateTime}
     */
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private int providerId;

    private float home;
    private float overUnder;
    private float away;

    private ZonedDateTime updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public float getHome() {
        return home;
    }

    public void setHome(float home) {
        this.home = home;
    }

    public float getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(float overUnder) {
        this.overUnder = overUnder;
    }

    public float getAway() {
        return away;
    }

    public void setAway(float away) {
        this.away = away;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
