package com.octopusneko.neko.miner.job;

import com.octopusneko.neko.miner.listener.HandicapListener;
import com.octopusneko.neko.miner.listener.event.ProviderEvent;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.service.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HandicapProviderJob extends Job {

    @Autowired
    private HandicapListener handicapListener;

    @Autowired
    private IRestService restService;

    private final Match match;

    public HandicapProviderJob(Match match) {
        super(new JobKey("HP_" + match.getId()));
        this.match = match;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(getJobKey().toString());
        downloadHandicapProviders(match);
    }

    private void downloadHandicapProviders(Match match) {
        List<Provider> providers = restService.downloadHandicapProviders(match);
        handicapListener.onApplicationEvent(new ProviderEvent(match, providers));
    }
}
