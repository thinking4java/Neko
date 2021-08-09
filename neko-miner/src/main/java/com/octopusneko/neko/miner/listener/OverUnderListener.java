package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.listener.event.ProviderEvent;
import com.octopusneko.neko.miner.model.OverUnder;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.payload.ProviderEntry;
import com.octopusneko.neko.miner.schedule.JobScheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import com.octopusneko.neko.miner.service.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("OverUnderListener")
public class OverUnderListener implements ApplicationListener<ProviderEvent> {

    @Autowired
    private IMatchService matchService;

    @Autowired
    private MatchConfig matchConfig;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private JobScheduler jobScheduler;

    @Override
    public void onApplicationEvent(ProviderEvent event) {
        List<Integer> handicapProviderIds = matchConfig.getOverUnderProviders().stream()
                .map(ProviderEntry::getId)
                .collect(Collectors.toList());
        List<Provider> filteredProviders = event.getProviders().stream()
                .filter(provider -> handicapProviderIds.contains(provider.getProviderId().getCode()))
                .collect(Collectors.toList());

        List<Provider> savedProviders = matchService.saveProviders(filteredProviders);
        savedProviders.forEach(provider -> jobScheduler.schedule(() -> downloadOverUnder(provider)));
    }

    private void downloadOverUnder(Provider provider) {
        List<OverUnder> overUnderList = restService.downloadOverUnder(provider);
        matchService.saveOverUnder(overUnderList);
    }
}
