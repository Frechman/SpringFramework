package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.store.QuizStore;

import java.util.List;

@Service
public class QuizDaoImpl implements QuizDao {

    private final QuizStore quizStore;

    @Autowired
    public QuizDaoImpl(final QuizStore quizStore) {
        this.quizStore = quizStore;
    }

    @Override
    public Quiz getQuizByQuestion(String question) {
        return quizStore.getAllData().stream()
                .filter(quiz -> quiz.getQuestion().equals(question))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizStore.getAllData();
    }
}
