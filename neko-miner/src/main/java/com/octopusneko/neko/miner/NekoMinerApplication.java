package com.octopusneko.neko.miner;

import com.octopusneko.neko.miner.config.ProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = ProviderConfig.class)
public class NekoMinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NekoMinerApplication.class, args);
	}

}
