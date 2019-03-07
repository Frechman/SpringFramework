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
        inOutService.ask("*** Приступим к тестированию? ***");
        inOutService.outputData("***     Деваться не куда!     ***");
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
        String message = String.format("Вопрос: %s%sВведите номер правильного ответа: ",
                quiz.getQuestion(), System.lineSeparator());
        return inOutService.ask(message);
    }

    @Override
    public String getResultTest(User user) {
        Integer countAnswer = userResults.get(user);
        return String.format("У %s количество правильных ответов: %s", user.getFullName(), countAnswer);
    }
}
