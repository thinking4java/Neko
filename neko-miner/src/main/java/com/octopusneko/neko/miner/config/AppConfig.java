package com.octopusneko.neko.miner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableConfigurationProperties(value = {MatchConfig.class, RestConfig.class})
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Bean
    public ApplicationListener<ContextClosedEvent> earlyTaskSchedulerShutdown(ThreadPoolTaskScheduler taskScheduler) {
        return event -> {
            logger.debug("@@@@@@@@@@@@@@@@@@@shutdown task scheduler@@@@@@@@@@@@@@@@22");
            taskScheduler.shutdown();
        };
    }
}
