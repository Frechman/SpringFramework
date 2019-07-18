package ru.gavrilov.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * Лишний бин. Добавлен лишь в целях обучения.
     */
    @Bean
    public DataSource dataSourcePostgres() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/library");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
    }
}
