package ru.gavrilov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:bundle.properties")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
//        ms.setBasename("/i18n/bundle");
        ms.setBasename("classpath:/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
