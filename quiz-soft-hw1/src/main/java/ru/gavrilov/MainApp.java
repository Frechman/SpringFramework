package ru.gavrilov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.gavrilov.service.quiz.QuizService;

public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        QuizService quizService = context.getBean(QuizService.class);

        quizService.runTest();
    }
}