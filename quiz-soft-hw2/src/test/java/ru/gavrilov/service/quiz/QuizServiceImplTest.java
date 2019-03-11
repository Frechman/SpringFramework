package ru.gavrilov.service.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.InputOutputService;
import ru.gavrilov.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizServiceImplTest {

    private UserService userService;
    private QuizDao quizDao;
    private InputOutputService inputOutputService;
    private List<Quiz> quizList;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        when(userService.saveUser("last", "first"))
                .thenReturn(new User(1, "last", "name"));
        quizDao = mock(QuizDao.class);
        quizList = Arrays.asList(
                new Quiz("q1", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3", 1, Arrays.asList("a1", "a2", "a3", "a4")));
        when(quizDao.getAllQuizzes()).thenReturn(quizList);
        inputOutputService = mock(InputOutputService.class);
    }

    @Test
    @Disabled
    public void runTest() {
        final QuizServiceImpl quizService = new QuizServiceImpl(quizDao, userService, inputOutputService);

        quizService.runTest();
    }
}