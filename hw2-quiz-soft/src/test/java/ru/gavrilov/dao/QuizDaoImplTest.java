package ru.gavrilov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.store.QuizStore;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizDaoImplTest {

    private List<Quiz> quizList = Arrays.asList(
            new Quiz("q1", 1, Arrays.asList("a1", "a2", "a3", "a4")),
            new Quiz("q2", 1, Arrays.asList("a1", "a2", "a3", "a4")),
            new Quiz("q3", 1, Arrays.asList("a1", "a2", "a3", "a4")));
    private QuizStore quizStore;
    private QuizDaoImpl quizDao;

    @BeforeEach
    public void init() {
        quizStore = mock(QuizStore.class);
        quizDao = new QuizDaoImpl(quizStore);
    }

    @Test
    @DisplayName("shouldCorrectQuiz")
    public void whenGetQuizByQuestionThenReturnCorrectQuiz() {
        Quiz testQuiz = new Quiz("test", 1, Arrays.asList("testA1", "a2", "testA3", "a4"));
        quizList.set(2, testQuiz);
        when(quizStore.getAllData()).thenReturn(quizList);

        Quiz actualQuiz = quizDao.getQuizByQuestion("test");
        assertThat(actualQuiz).isEqualToComparingFieldByFieldRecursively(testQuiz);

        Quiz actualNotContainsQuiz = quizDao.getQuizByQuestion("q3");
        assertThat(actualNotContainsQuiz).isNull();
    }

    @Test
    public void whenGetAllQuizzesThenReturnQuizList() {
        when(quizStore.getAllData()).thenReturn(quizList);

        List<Quiz> actualQuizzes = quizDao.getAllQuizzes();

        assertThat(actualQuizzes).hasSize(3).containsAll(quizList);
    }
}