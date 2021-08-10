package com.octopusneko.neko.miner.listener.event;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class ProviderEvent extends ApplicationEvent {

    private final transient Match match;
    private final transient List<Provider> providers;

    public ProviderEvent(Match match, List<Provider> providers) {
        super("");
        this.match = match;
        this.providers = providers;
    }

    public Match getMatch() {
        return match;
    }

    public final List<Provider> getProviders() {
        return providers;
    }


}
