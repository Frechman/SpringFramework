package ru.gavrilov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.gavrilov.presentation.PresenterQuizService;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        PresenterQuizService presenterQuizService = context.getBean(PresenterQuizService.class);

        presenterQuizService.runTest();
    }
}