package com.octopusneko.neko.miner.schedule;

import com.octopusneko.neko.miner.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


@Component
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    private final ExecutorService workThreadPool;

    private final BlockingQueue<Runnable> workQueue;

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final AtomicLong counter = new AtomicLong(0);

    public JobScheduler(@Value("${app.job.poolSize: 2}") int poolSize
            , @Value("${app.job.queueSize:100}") int queueSize
            , @Value("${app.job.minDelay:2000}") long minDelay
            , @Value("${app.job.maxDelay:5000}") long maxDelay) {
        workThreadPool = Executors.newFixedThreadPool(poolSize);
        workQueue = new ArrayBlockingQueue<>(queueSize);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (running.get()) {
                    long randomFixedDelay = getRandomRangeDelay(minDelay, maxDelay);
                    logger.trace("Random fixed delay {}", randomFixedDelay);
                    Thread.sleep(randomFixedDelay);
                    Future<?> future = workThreadPool.submit(workQueue.take());
                    future.get();
                    counter.decrementAndGet();
                }
            } catch (Exception e) {
                logger.error("Failed to take from work queue", e);
                Thread.currentThread().interrupt();
            }
        });
    }

    public void schedule(Job job) {
        if(workQueue.offer(job)) {
            counter.incrementAndGet();
        }
    }

    private long getRandomRangeDelay(long minDelay, long maxDelay) {
        return new Random().longs(minDelay, maxDelay).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid range between " + minDelay + " and " + maxDelay));
    }

    public void dump() {
        logger.debug("========================================");
        logger.debug("Currently job queue size: {}", counter.get());
        logger.debug("========================================");
    }

}
