package com.octopusneko.neko.miner;

import com.octopusneko.neko.miner.schedule.JobScheduler;
import com.octopusneko.neko.miner.service.IMatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class NekoMinerApplicationTests {

    @Autowired
    private IMatchService matchService;

    @MockBean
    private JobScheduler JobScheduler;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(matchService);
    }

}
