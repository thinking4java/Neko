package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.config.ProviderConfig;
import com.octopusneko.neko.miner.listener.event.HandicapEvent;
import com.octopusneko.neko.miner.model.Handicap;
import com.octopusneko.neko.miner.model.OverUnder;
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

@Component
public class HandicapListener implements ApplicationListener<HandicapEvent> {

    @Autowired
    private IMatchService matchService;

    @Autowired
    private ProviderConfig providerConfig;

    @Autowired
    private RestService restService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(HandicapEvent event) {
        List<Integer> handicapProviderIds = providerConfig.getHandicapProviders().stream()
                .map(ProviderEntry::getId)
                .collect(Collectors.toList());

        List<Provider> filteredProviders = event.getProviders().stream()
                .filter(provider -> handicapProviderIds.contains(provider.getProviderId().getCode()))
                .collect(Collectors.toList());
        List<Provider> savedProviders = matchService.saveProviders(filteredProviders);
        savedProviders.forEach(provider -> {
            scheduler.schedule(() -> downloadHandicap(provider));
            scheduler.schedule(() -> downloadOverUnder(provider));
        });
    }

    private void downloadHandicap(Provider provider) {
        List<Handicap> handicaps = restService.downloadHandicap(provider);
        matchService.saveHandicap(handicaps);
    }

    private void downloadOverUnder(Provider provider) {
        List<OverUnder> overUnderList = restService.downloadOverUnder(provider);
        matchService.saveOverUnder(overUnderList);
    }
}
