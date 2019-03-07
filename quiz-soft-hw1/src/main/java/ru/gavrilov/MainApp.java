package ru.gavrilov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.gavrilov.service.quiz.QuizService;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        QuizService quizService = context.getBean(QuizService.class);

        quizService.runTest();
    }
}
