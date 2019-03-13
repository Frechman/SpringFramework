package ru.gavrilov.service.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.InputOutputService;
import ru.gavrilov.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    private final Map<User, Integer> userResults = new HashMap<>();

    private final QuizDao quizDao;
    private final UserService userService;
    private final InputOutputService inOutService;
    private final MessageSource msgSource;
    private Locale locale;

    @Autowired
    public QuizServiceImpl(final QuizDao quizDao, final UserService userService,
                           final InputOutputService inputOutputService, final MessageSource messageSource) {
        this.quizDao = quizDao;
        this.userService = userService;
        this.inOutService = inputOutputService;
        this.msgSource = messageSource;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    @Override
    public void runTest() {
        this.locale = askUserLocale();
        User user = askUserCredentials();
        int countCorrectAnswer = 0;
        inOutService.outputData(getMsgLocale("msg.enter.ready"));
        for (Quiz q : getAllQuizzes()) {
            String answer = askQuestion(q);
            if (q.isCorrectAnswer(Integer.valueOf(answer))) {
                countCorrectAnswer++;
            }
        }
        userResults.put(user, countCorrectAnswer);
        inOutService.outputData(getMsgLocale("msg.label.test-finish"));
        inOutService.outputData(getMsgLocale("msg.label.your-result") + getResultTest(user));
    }

    private Locale askUserLocale() {
        final String russian = getMsgLocale("msg.label.russian-lang");
        final String english = getMsgLocale("msg.label.english-lang");
        final String selectedLanguage = getMsgLocale("msg.enter.selected-language");
        final String ask = inOutService.ask(selectedLanguage + russian + " / " + english);
        return ask.equals(english) ? Locale.ENGLISH : new Locale("ru", "Ru");
    }

    private User askUserCredentials() {
        String lastName = inOutService.ask(getMsgLocale("msg.enter.last-name"));
        String firstName = inOutService.ask(getMsgLocale("msg.enter.first-name"));
        return userService.saveUser(lastName, firstName);
    }

    private String askQuestion(Quiz quiz) {
        String sb = getMsgLocale("msg.label.question") + quiz.getQuestion() +
                System.lineSeparator() +
                getMsgLocale("msg.label.answer-choice") + quiz.getAnswers().toString() +
                System.lineSeparator() +
                getMsgLocale("msg.enter.correct-answer");
        return inOutService.ask(sb);
    }

    @Override
    public String getResultTest(User user) {
        Integer countAnswer = userResults.get(user);
        return countAnswer == null ? getMsgLocale("msg.label.user-not-found") :
                getMsgLocale("msg.label.result", new Object[] {user.getFullName(), countAnswer});
    }

    private String getMsgLocale(String param, Object[] objects) {
        return msgSource.getMessage(param, objects, this.locale);
    }

    private String getMsgLocale(String param) {
        return getMsgLocale(param, null);
    }
}
