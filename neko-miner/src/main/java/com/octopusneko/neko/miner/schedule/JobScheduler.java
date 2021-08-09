package com.octopusneko.neko.miner.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    @Value("${app.job.minDelay:2000}")
    private long minDelay;

    @Value("${app.job.maxDelay:5000}")
    private long maxDelay;

    private final ExecutorService workThreadPool;

    private final BlockingQueue<Runnable> workQueue;

    private final AtomicBoolean running = new AtomicBoolean(true);

    public JobScheduler(@Value("${app.job.poolSize: 2}") int poolSize) {
        workThreadPool = Executors.newFixedThreadPool(poolSize);
        workQueue = new LinkedBlockingQueue<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (running.get()) {
                    long randomFixedDelay = getRandomRangeDelay(minDelay, maxDelay);
                    logger.trace("Random fixed delay {}", randomFixedDelay);
                    Thread.sleep(randomFixedDelay);
                    workThreadPool.submit(workQueue.take());
                }
            } catch (InterruptedException e) {
                logger.error("Failed to take from work queue", e);
                Thread.currentThread().interrupt();
            }
        });
    }

    public void schedule(Runnable job) {
        workQueue.add(job);
    }

    private long getRandomRangeDelay(long minDelay, long maxDelay) {
        return new Random().longs(minDelay, maxDelay).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid range between " + minDelay + " and " + maxDelay));
    }

}
