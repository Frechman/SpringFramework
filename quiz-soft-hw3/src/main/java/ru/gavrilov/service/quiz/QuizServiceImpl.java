package ru.gavrilov.service.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    private final Map<User, Integer> userResults = new HashMap<>();

    private final QuizDao quizDao;

    @Autowired
    public QuizServiceImpl(final QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    @Override
    public Integer getResultTest(User user) {
        return userResults.get(user);
    }

    @Override
    public void saveUserResultTest(User user, Integer countCorrectAnswer) {
        userResults.put(user, countCorrectAnswer);
    }
}
