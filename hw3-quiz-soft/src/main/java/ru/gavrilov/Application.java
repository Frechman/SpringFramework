package ru.gavrilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gavrilov.presentation.PresenterQuizService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).getBean(PresenterQuizService.class).runTest();
    }
}