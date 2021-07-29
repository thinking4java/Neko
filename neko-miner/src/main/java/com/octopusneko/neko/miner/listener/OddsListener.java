package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.listener.event.ProviderEvent;
import com.octopusneko.neko.miner.model.Odds;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.payload.ProviderEntry;
import com.octopusneko.neko.miner.schedule.Scheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import com.octopusneko.neko.miner.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("OddsListener")
public class OddsListener implements ApplicationListener<ProviderEvent> {

    @Autowired
    private IMatchService matchService;

    @Autowired
    private MatchConfig matchConfig;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private RestService restService;

    @Override
    public void onApplicationEvent(ProviderEvent event) {
        List<Integer> oddProviderIds = matchConfig.getOddsProviders().stream()
                .map(ProviderEntry::getId)
                .collect(Collectors.toList());
        List<Provider> filteredProviders = event.getProviders().stream()
                .filter(provider -> oddProviderIds.contains(provider.getProviderId().getCode()))
                .collect(Collectors.toList());

        List<Provider> savedProviders = matchService.saveProviders(filteredProviders);
        savedProviders.forEach(provider -> scheduler.schedule(() -> downloadOdds(provider)));
    }

    private void downloadOdds(Provider provider) {
        List<Odds> oddsList = restService.downloadOdds(provider);
        matchService.saveOdds(oddsList);
    }
}
