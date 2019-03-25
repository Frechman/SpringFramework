package ru.gavrilov.service.quiz;

import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;

import java.util.List;

public interface QuizService {

    List<Quiz> getAllQuizzes();

    void saveUserResultTest(User user, Integer countCorrectAnswer);

    Integer getResultTest(User user);

    String getAllResult();
}
