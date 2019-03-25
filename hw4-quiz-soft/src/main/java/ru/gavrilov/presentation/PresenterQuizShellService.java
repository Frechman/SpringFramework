package ru.gavrilov.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.quiz.QuizService;
import ru.gavrilov.service.user.UserService;

import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

@ShellComponent
public class PresenterQuizShellService implements PresenterQuizService {

    private final Scanner sc = new Scanner(System.in);

    private final UserService userService;
    private final MessageSource msgSource;
    private final QuizService quizService;

    @Autowired
    public PresenterQuizShellService(final QuizService quizService, final UserService userService,
                                     final MessageSource messageSource) {
        this.userService = userService;
        this.msgSource = messageSource;
        this.quizService = quizService;
    }

    @ShellMethod("Start testing.")
    public String runTest() {
        int countCorrectAnswer = 0;
        this.outputData(getMsgLocale("msg.enter.ready"));
        for (Quiz q : quizService.getAllQuizzes()) {
            String answer = askQuestion(q);
            if (q.isCorrectAnswer(Integer.valueOf(answer))) {
                countCorrectAnswer++;
            }
        }
        LinkedList<User> all = new LinkedList<>(userService.getAll());
        if (!all.isEmpty()) {
            quizService.saveUserResultTest(all.getLast(), countCorrectAnswer);
        }
        return getMsgLocale("msg.label.test-finish");
    }

    @ShellMethod(value = "Set user for testing.", prefix = "-", key = "set-user")
    public String setUserTest(@ShellOption({"-l", "-lastName"}) String lastName,
                              @ShellOption({"-f", "-firstName"}) String firstName) {
        userService.saveUser(lastName, firstName);
        return String.format("%s %s %s", getMsgLocale("msg.label.hello"), lastName, firstName);
    }

    @ShellMethod(value = "Print result test. Default print for all users.", key = "result-test")
    public String getResult(@ShellOption(defaultValue = "--all") String lastName) {
        if (lastName.equals("--all")) {
            return quizService.getAllResult();
        }
        User foundUser = userService.getUserByLastName(lastName);
        Integer countAnswer = quizService.getResultTest(foundUser);
        return countAnswer == null ? getMsgLocale("msg.label.user-not-found") :
                getMsgLocale("msg.label.result", new Object[]{foundUser.getFullName(), countAnswer});
    }

    @ShellMethod(value = "Setting language.", key = "set-lang")
    public String setLanguage(@Pattern(regexp = "^(ru|RU|en|EN)$", message = "Invalid code. Choose from en\\EN\\ru\\RU")
                              @ShellOption(defaultValue = "en") String locale) {
        Locale.setDefault(new Locale(locale.toLowerCase(), locale.toUpperCase()));
        String localeName = Locale.getDefault().getDisplayName();
        return String.format("%s %s", getMsgLocale("msg.enter.selected-language"), localeName);
    }

    /*С вопросами для ввода имени и фамилии.
    @ShellMethod(value = "Set user for testing.", key = "set-user")
    public String setUserTest() {
        String lastName = this.ask(getMsgLocale("msg.enter.last-name"));
        String firstName = this.ask(getMsgLocale("msg.enter.first-name"));
        userService.saveUser(lastName, firstName);
        return String.format("Hello %s %s!", lastName, firstName);
    }*/

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

    private String askQuestion(Quiz quiz) {
        String sb = getMsgLocale("msg.label.question") + quiz.getQuestion() +
                System.lineSeparator() +
                getMsgLocale("msg.label.answer-choice") + quiz.getAnswers().toString() +
                System.lineSeparator() +
                getMsgLocale("msg.enter.correct-answer");
        return this.ask(sb);
    }

    private String getMsgLocale(String param, Object[] objects) {
        return msgSource.getMessage(param, objects, Locale.getDefault());
    }

    private String getMsgLocale(String param) {
        return getMsgLocale(param, null);
    }
}
