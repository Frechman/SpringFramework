package ru.gavrilov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.store.QuizStore;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class QuizDaoImplTest {

    @Autowired
    public QuizDao quizDao;
    @MockBean
    private QuizStore quizStore;
    private List<Quiz> quizList = Arrays.asList(
            new Quiz("q1", 1, Arrays.asList("a1", "a2", "a3", "a4")),
            new Quiz("q2", 1, Arrays.asList("a1", "a2", "a3", "a4")),
            new Quiz("q3", 1, Arrays.asList("a1", "a2", "a3", "a4")));

    @Test
    @DisplayName("shouldCorrectQuiz")
    void whenGetQuizByQuestionThenReturnCorrectQuiz() {
        Quiz testQuiz = new Quiz("test", 1, Arrays.asList("testA1", "a2", "testA3", "a4"));
        quizList.set(2, testQuiz);
        when(quizStore.getAllData()).thenReturn(quizList);

        Quiz actualQuiz = quizDao.getQuizByQuestion("test");
        assertThat(actualQuiz).isEqualToComparingFieldByFieldRecursively(testQuiz);

        Quiz actualNotContainsQuiz = quizDao.getQuizByQuestion("q3");
        assertThat(actualNotContainsQuiz).isNull();
    }

    @Test
    void whenGetAllQuizzesThenReturnQuizList() {
        when(quizStore.getAllData()).thenReturn(quizList);

        List<Quiz> actualQuizzes = quizDao.getAllQuizzes();

        assertThat(actualQuizzes).hasSize(3).containsAll(quizList);
    }
}