package com.octopusneko.neko.miner.config;

import com.octopusneko.neko.miner.payload.ProviderEntry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.match")
public class ProviderConfig {

    private List<ProviderEntry> handicapProviders;

    private List<ProviderEntry> oddsProviders;

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
}
