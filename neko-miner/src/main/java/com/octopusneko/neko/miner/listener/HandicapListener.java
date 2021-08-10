package com.octopusneko.neko.miner.listener;

import com.octopusneko.neko.miner.config.MatchConfig;
import com.octopusneko.neko.miner.job.HandicapJob;
import com.octopusneko.neko.miner.listener.event.ProviderEvent;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.payload.Entry;
import com.octopusneko.neko.miner.schedule.JobScheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandicapListener implements ApplicationListener<ProviderEvent> {

    @Autowired
    private IMatchService matchService;

    @Autowired
    private MatchConfig matchConfig;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ProviderEvent event) {
        List<Provider> providers = event.getProviders();
        List<Provider> filteredProviders = getFilteredProviders(providers);
        List<Provider> savedProviders = matchService.saveProviders(filteredProviders);
        Match match = event.getMatch();
        savedProviders.forEach(provider -> {
            HandicapJob handicapJob = applicationContext.getBean(HandicapJob.class, match, provider);
            jobScheduler.schedule(handicapJob);
        });
    }

    private List<Provider> getFilteredProviders(List<Provider> providers) {
        List<Integer> handicapProviderIds = matchConfig.getHandicapProviders().stream()
                .map(Entry::getId)
                .collect(Collectors.toList());
        return providers.stream()
                .filter(provider -> handicapProviderIds.contains(provider.getProviderId().getCode()))
                .collect(Collectors.toList());
    }
}
