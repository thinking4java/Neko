package com.octopusneko.neko.miner.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
public class Scheduler {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public ScheduledFuture<?> schedule(Runnable task) {
        return threadPoolTaskScheduler.schedule(task, new Date());
    }
}
