package com.octopusneko.neko.miner.job;

import com.octopusneko.neko.miner.listener.OddsListener;
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
public class OddsProviderJob extends Job {

    @Autowired
    private OddsListener oddsListener;

    @Autowired
    private IRestService restService;

    private final Match match;

    public OddsProviderJob(Match match) {
        super(new JobKey("OP_" + match.getId()));
        this.match = match;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(getJobKey().toString());
        downloadHandicapProviders(match);
    }

    private void downloadHandicapProviders(Match match) {
        List<Provider> providers = restService.downloadOddsProviders(match);
        oddsListener.onApplicationEvent(new ProviderEvent(match, providers));
    }
}
