package com.octopusneko.neko.miner.config;

import com.octopusneko.neko.miner.schedule.ScheduleTasks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ProviderConfig.class)
class ProviderConfigTest {

    @Autowired
    private ProviderConfig config;

    @MockBean
    private ScheduleTasks scheduleTasks;

    @Test
    void testLoadConfig() {
        Assertions.assertNotNull(config);
        Assertions.assertNotNull(config.getHandicapProviders());
        Assertions.assertEquals(2, config.getHandicapProviders().size());
        Assertions.assertEquals(9, config.getHandicapProviders().get(0).getId());

        Assertions.assertNotNull(config.getOddsProviders());
        Assertions.assertEquals(2, config.getOddsProviders().size());
        Assertions.assertEquals(80, config.getOddsProviders().get(1).getId());
    }

}