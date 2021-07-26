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
        Assertions.assertEquals(6, config.getHandicapProviders().size());
        Assertions.assertEquals(1, config.getHandicapProviders().get(0).getId());
        Assertions.assertEquals(14, config.getHandicapProviders().get(config.getHandicapProviders().size() - 1).getId());

        Assertions.assertNotNull(config.getOddsProviders());
        Assertions.assertEquals(7, config.getOddsProviders().size());
        Assertions.assertEquals(80, config.getOddsProviders().get(0).getId());
        Assertions.assertEquals(82, config.getOddsProviders().get(config.getOddsProviders().size() - 1).getId());

        Assertions.assertNotNull(config.getOverUnderProviders());
        Assertions.assertEquals(7, config.getOverUnderProviders().size());
        Assertions.assertEquals(1, config.getOverUnderProviders().get(0).getId());
        Assertions.assertEquals(4, config.getOverUnderProviders().get(config.getOverUnderProviders().size() - 1).getId());
    }

}