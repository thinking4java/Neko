package com.octopusneko.neko.miner.listener.event;

import com.octopusneko.neko.miner.model.Provider;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class HandicapEvent extends ApplicationEvent {

    public HandicapEvent(List<Provider> leagues) {
        super(leagues);
    }

    public final List<Provider> getProviders() {
        return (List<Provider>) getSource();
    }
}
