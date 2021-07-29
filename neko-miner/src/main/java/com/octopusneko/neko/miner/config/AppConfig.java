package com.octopusneko.neko.miner.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableRetry
@EnableScheduling
@EnableConfigurationProperties(value = {MatchConfig.class, RestConfig.class})
public class AppConfig {

}
