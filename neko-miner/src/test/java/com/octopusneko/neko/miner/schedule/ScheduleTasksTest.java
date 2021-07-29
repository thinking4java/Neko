package com.octopusneko.neko.miner.schedule;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleTasksTest {

    @SpyBean
    private ScheduleTasks scheduleTasks;

    @Test
    void test_updateMatch_atLeast() {
        // since fixed rate is 5s

        // in 10s, at least executed 1 times
        Awaitility
                .await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> Mockito.verify(scheduleTasks, Mockito.atLeast(1)).updateMatch());
    }

}