package com.octopusneko.neko.miner.config;

import com.octopusneko.neko.miner.payload.ProviderEntry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.match")
public class MatchConfig {

    private String baseUrl = "https://m.nowscore.com";

    private String matchListPath;

    private String handicapPath;
    private String handicapDetailPath;

    private String oddsPath;
    private String oddsDetailPath;

    private String overUnderPath;
    private String overUnderDetailPath;


    private List<ProviderEntry> handicapProviders;
    private List<ProviderEntry> oddsProviders;
    private List<ProviderEntry> overUnderProviders;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMatchListPath() {
        return matchListPath;
    }

    public void setMatchListPath(String matchListPath) {
        this.matchListPath = matchListPath;
    }

    public String getHandicapPath() {
        return handicapPath;
    }

    public void setHandicapPath(String handicapPath) {
        this.handicapPath = handicapPath;
    }

    public String getHandicapDetailPath() {
        return handicapDetailPath;
    }

    public void setHandicapDetailPath(String handicapDetailPath) {
        this.handicapDetailPath = handicapDetailPath;
    }

    public String getOddsPath() {
        return oddsPath;
    }

    public void setOddsPath(String oddsPath) {
        this.oddsPath = oddsPath;
    }

    public String getOddsDetailPath() {
        return oddsDetailPath;
    }

    public void setOddsDetailPath(String oddsDetailPath) {
        this.oddsDetailPath = oddsDetailPath;
    }

    public String getOverUnderPath() {
        return overUnderPath;
    }

    public void setOverUnderPath(String overUnderPath) {
        this.overUnderPath = overUnderPath;
    }

    public String getOverUnderDetailPath() {
        return overUnderDetailPath;
    }

    public void setOverUnderDetailPath(String overUnderDetailPath) {
        this.overUnderDetailPath = overUnderDetailPath;
    }

    public List<ProviderEntry> getHandicapProviders() {
        return handicapProviders;
    }

    public void setHandicapProviders(List<ProviderEntry> handicapProviders) {
        this.handicapProviders = handicapProviders;
    }

    public List<ProviderEntry> getOddsProviders() {
        return oddsProviders;
    }

    public void setOddsProviders(List<ProviderEntry> oddsProviders) {
        this.oddsProviders = oddsProviders;
    }

    public List<ProviderEntry> getOverUnderProviders() {
        return overUnderProviders;
    }

    public void setOverUnderProviders(List<ProviderEntry> overUnderProviders) {
        this.overUnderProviders = overUnderProviders;
    }
}
