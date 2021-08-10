package com.octopusneko.neko.miner.job;

import com.octopusneko.neko.miner.listener.OverUnderListener;
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
public class OverUnderProviderJob extends Job {

    @Autowired
    private OverUnderListener overUnderListener;

    @Autowired
    private IRestService restService;

    private final Match match;

    public OverUnderProviderJob(Match match) {
        super(new JobKey("OP_" + match.getId()));
        this.match = match;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(getJobKey().toString());
        downloadHandicapProviders(match);
    }

    private void downloadHandicapProviders(Match match) {
        List<Provider> providers = restService.downloadOverUnderProviders(match);
        overUnderListener.onApplicationEvent(new ProviderEvent(match, providers));
    }
}
