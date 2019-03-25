package ru.gavrilov.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.quiz.QuizService;
import ru.gavrilov.service.user.UserService;

import java.util.Locale;
import java.util.Scanner;

@Service
public class PresenterQuizConsoleService implements PresenterQuizService {

    private final Scanner sc = new Scanner(System.in);

    private final UserService userService;
    private final MessageSource msgSource;
    private final QuizService quizService;
    private Locale locale;

    @Autowired
    public PresenterQuizConsoleService(final QuizService quizService, final UserService userService,
                                       final MessageSource messageSource, @Value("${test.locale}") String systemLocale) {
        this.userService = userService;
        this.msgSource = messageSource;
        this.quizService = quizService;
        this.locale = new Locale(systemLocale.toLowerCase(), systemLocale.toUpperCase());
    }

    @Override
    public void outputData(String message) {
        System.out.println(message);
    }

    @Override
    public String inputData() {
        return sc.nextLine();
    }

    @Override
    public String ask(String question) {
        System.out.print(question);
        return inputData();
    }

    @Override
    public void runTest() {
        User user = askUserCredentials();
        int countCorrectAnswer = 0;
        this.outputData(getMsgLocale("msg.enter.ready"));
        for (Quiz q : quizService.getAllQuizzes()) {
            String answer = askQuestion(q);
            if (q.isCorrectAnswer(Integer.valueOf(answer))) {
                countCorrectAnswer++;
            }
        }
        quizService.saveUserResultTest(user, countCorrectAnswer);
        this.outputData(getMsgLocale("msg.label.test-finish"));

        this.outputData(getMsgLocale("msg.label.your-result") + getResult(user));
    }

    private String getResult(User user) {
        Integer countAnswer = quizService.getResultTest(user);
        return countAnswer == null ? getMsgLocale("msg.label.user-not-found") :
                getMsgLocale("msg.label.result", new Object[]{user.getFullName(), countAnswer});
    }

    private User askUserCredentials() {
        String lastName = this.ask(getMsgLocale("msg.enter.last-name"));
        String firstName = this.ask(getMsgLocale("msg.enter.first-name"));
        return userService.saveUser(lastName, firstName);
    }

    private String askQuestion(Quiz quiz) {
        String sb = getMsgLocale("msg.label.question") + quiz.getQuestion() +
                System.lineSeparator() +
                getMsgLocale("msg.label.answer-choice") + quiz.getAnswers().toString() +
                System.lineSeparator() +
                getMsgLocale("msg.enter.correct-answer");
        return this.ask(sb);
    }

    private String getMsgLocale(String param, Object[] objects) {
        return msgSource.getMessage(param, objects, this.locale);
    }

    private String getMsgLocale(String param) {
        return getMsgLocale(param, null);
    }

    /*  Закомментированный код хранить в гите плохо (c)
        private Locale askUserLocale() {
        final String russian = getMsgLocale("msg.label.russian-lang");
        final String english = getMsgLocale("msg.label.english-lang");
        final String selectedLanguage = getMsgLocale("msg.enter.selected-language");
        final String ask = inOutService.ask(selectedLanguage + russian + " / " + english);
        return ask.equals(english) ? Locale.ENGLISH : new Locale("ru", "Ru");
    }*/
}
