package com.octopusneko.neko.miner;

import com.octopusneko.neko.miner.service.IMatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NekoMinerApplicationTests {

    @Autowired
    private IMatchService matchService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(matchService);
    }

}
