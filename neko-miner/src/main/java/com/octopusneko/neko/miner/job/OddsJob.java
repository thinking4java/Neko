package com.octopusneko.neko.miner.job;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Odds;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.service.IMatchService;
import com.octopusneko.neko.miner.service.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OddsJob extends Job {

    private final Match match;
    private final Provider provider;

    public OddsJob(Match match, Provider provider) {
        super(new JobKey("O_" + match.getId() + "_" + provider.getProviderId().getCode()));
        this.match = match;
        this.provider = provider;
    }

    @Autowired
    private IRestService restService;

    @Autowired
    private IMatchService matchService;

    @Override
    public void run() {
        Thread.currentThread().setName(getJobKey().toString());
        downloadOdds(match, provider);
    }

    private void downloadOdds(Match match, Provider provider) {
        List<Odds> oddsList = restService.downloadOdds(match, provider);
        matchService.saveOdds(oddsList);
    }
}
