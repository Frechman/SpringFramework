package ru.gavrilov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gavrilov.presentation.PresenterQuizService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final PresenterQuizService presenterQuizService;

    @Autowired
    public Application(PresenterQuizService presenterQuizService) {
        this.presenterQuizService = presenterQuizService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        presenterQuizService.runTest();
    }
}