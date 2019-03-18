package ru.gavrilov;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
//Специально, чтобы сохранить пример в репозитории.
//@SpringBootTest(classes = TestConfig.class) - используем над тест класс

//Отдельный тестовый конфиг, чтобы не использовать поднятие контекста из @SpringBootApplication public class Application,
//который исключает CommandLineRunner из-за которого тесты не могут выполниться.
//@Configuration
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
//@EnableAutoConfiguration
public class TestConfig {
}
