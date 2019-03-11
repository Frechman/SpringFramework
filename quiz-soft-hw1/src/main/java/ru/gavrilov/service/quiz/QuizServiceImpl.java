package ru.gavrilov.service.quiz;

import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.InputOutputService;
import ru.gavrilov.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizServiceImpl implements QuizService {

    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private final QuizDao quizDao;
    private final UserService userService;
    private final InputOutputService inOutService;
    private final Map<User, Integer> userResults = new HashMap<>();

    public QuizServiceImpl(final UserService userService, final QuizDao quizDao,
                           final InputOutputService inputOutputService) {
        this.userService = userService;
        this.inOutService = inputOutputService;
        this.quizDao = quizDao;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuiz();
    }

    @Override
    public void runTest() {
        String lastName = inOutService.ask("Введите фамилию: ");
        String firstName = inOutService.ask("Введите имя: ");
        User user = userService.saveUser(lastName, firstName);
        int countCorrectAnswer = 0;
        inOutService.outputData("*** Приступим к тестированию? ***");
        for (Quiz q : getAllQuizzes()) {
            String answer = askQuestion(q);
            if (q.isCorrectAnswer(Integer.valueOf(answer))) {
                countCorrectAnswer++;
            }
        }
        userResults.put(user, countCorrectAnswer);
        inOutService.outputData("*** Тест завершен! ***");
        inOutService.outputData("Ваши результаты:" + getResultTest(user));
    }

    @Override
    public String askQuestion(Quiz quiz) {
        String sb = "Вопрос: " + quiz.getQuestion() +
                System.lineSeparator() +
                "Варианты ответа:" + quiz.getAnswers().toString() +
                System.lineSeparator() +
                "Введите номер правильного ответа: ";
        return inOutService.ask(sb);
    }

    @Override
    public String getResultTest(User user) {
        Integer countAnswer = userResults.get(user);
        return countAnswer == null ? USER_NOT_FOUND :
                String.format("У %s количество правильных ответов: %s", user.getFullName(), countAnswer);
    }
}
