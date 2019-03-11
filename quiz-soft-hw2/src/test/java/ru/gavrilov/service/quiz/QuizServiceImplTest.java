package ru.gavrilov.service.quiz;

import org.junit.Before;
import org.junit.Test;
import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;
import ru.gavrilov.service.InputOutputService;
import ru.gavrilov.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizServiceImplTest {
/*
    private UserService userService;
    private QuizDao quizDao;
    private InputOutputService inputOutputService;
    private List<Quiz> quizList;
    private QuizServiceImpl quizService;

    @Before
    public void init() {
        userService = mock(UserService.class);
        when(userService.saveUser("last", "first"))
                .thenReturn(new User(1, "last", "name"));
        quizDao = mock(QuizDao.class);
        quizList = Arrays.asList(
                new Quiz("q1", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3", 1, Arrays.asList("a1", "a2", "a3", "a4")));
        when(quizDao.getAllQuiz()).thenReturn(quizList);
        inputOutputService = mock(InputOutputService.class);

        quizService = new QuizServiceImpl(userService, quizDao, inputOutputService);
    }

    @Test
    public void getAllQuizzes() {
        List<Quiz> actualQuizzes = quizService.getAllQuizzes();
        assertThat(actualQuizzes).containsAll(quizList);
    }*/
}