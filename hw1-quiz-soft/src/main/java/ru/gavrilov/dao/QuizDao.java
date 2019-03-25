package ru.gavrilov.dao;

import ru.gavrilov.model.Quiz;

import java.util.List;

public interface QuizDao {

    Quiz getQuizByQuestion(String question);

    List<Quiz> getAllQuiz();
}
