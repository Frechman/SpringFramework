package ru.gavrilov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.gavrilov.service.quiz.QuizService;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        QuizService quizService = context.getBean(QuizService.class);

        quizService.runTest();
    }
}