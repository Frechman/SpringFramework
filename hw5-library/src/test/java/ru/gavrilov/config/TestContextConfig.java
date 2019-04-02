package ru.gavrilov.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.gavrilov.config", "ru.gavrilov.dao", "ru.gavrilov.service"})
public class TestContextConfig {
}
