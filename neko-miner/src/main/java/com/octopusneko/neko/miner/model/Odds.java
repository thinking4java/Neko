package com.octopusneko.neko.miner.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class Odds {

    /**
     * id = {matchId}-{providerId}-{updateTime}
     */
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private int providerId;

    private BigDecimal home;
    private BigDecimal draw;
    private BigDecimal away;

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

    public BigDecimal getHome() {
        return home;
    }

    public void setHome(BigDecimal home) {
        this.home = home;
    }

    public BigDecimal getDraw() {
        return draw;
    }

    public void setDraw(BigDecimal draw) {
        this.draw = draw;
    }

    public BigDecimal getAway() {
        return away;
    }

    public void setAway(BigDecimal away) {
        this.away = away;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
