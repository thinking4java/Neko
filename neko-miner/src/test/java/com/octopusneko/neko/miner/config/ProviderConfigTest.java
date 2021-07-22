package com.octopusneko.neko.miner.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ProviderConfig.class)
class ProviderConfigTest {

    @Autowired
    private ProviderConfig config;

    @Test
    void testLoadConfig() {
        Assertions.assertNotNull(config);
        Assertions.assertNotNull(config.getAsiaProviders());
        Assertions.assertEquals(2, config.getAsiaProviders().size());
        Assertions.assertEquals(9, config.getAsiaProviders().get(0).getId());

        Assertions.assertNotNull(config.getEuroProviders());
        Assertions.assertEquals(2, config.getEuroProviders().size());
        Assertions.assertEquals(80, config.getEuroProviders().get(1).getId());
    }

}