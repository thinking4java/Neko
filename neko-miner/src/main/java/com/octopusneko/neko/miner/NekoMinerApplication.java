package com.octopusneko.neko.miner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NekoMinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NekoMinerApplication.class, args);
	}

}
