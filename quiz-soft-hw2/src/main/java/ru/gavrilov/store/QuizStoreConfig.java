package ru.gavrilov.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:Application.properties")
@Configuration
public class QuizStoreConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
