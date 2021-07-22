package com.octopusneko.neko.miner.config;

import com.octopusneko.neko.miner.model.Provider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.match")
public class ProviderConfig {

    private List<Provider> asiaProviders;

    private List<Provider> euroProviders;

    public List<Provider> getAsiaProviders() {
        return asiaProviders;
    }

    public void setAsiaProviders(List<Provider> asiaProviders) {
        this.asiaProviders = asiaProviders;
    }

    public List<Provider> getEuroProviders() {
        return euroProviders;
    }

    public void setEuroProviders(List<Provider> euroProviders) {
        this.euroProviders = euroProviders;
    }
}
