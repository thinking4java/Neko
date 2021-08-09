package com.octopusneko.neko.miner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfig {

    @Bean("threadPoolTaskScheduler")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(@Value("${app.schedule.poolSize}") int poolSize) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix("sched-");
        scheduler.initialize();
        return scheduler;
    }

}