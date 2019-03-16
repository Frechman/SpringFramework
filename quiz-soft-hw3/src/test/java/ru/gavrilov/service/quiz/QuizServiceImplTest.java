package ru.gavrilov.service.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.gavrilov.dao.QuizDao;
import ru.gavrilov.model.Quiz;
import ru.gavrilov.model.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuizServiceImplTest {

    private QuizDao quizDao;
    private List<Quiz> quizList;
    private QuizServiceImpl quizService;

    @BeforeEach
    void init() {
        quizDao = mock(QuizDao.class);
        quizList = Arrays.asList(
                new Quiz("q1", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3", 1, Arrays.asList("a1", "a2", "a3", "a4")));
        when(quizDao.getAllQuizzes()).thenReturn(quizList);
        this.quizService = new QuizServiceImpl(quizDao);
    }

    @Test
    @DisplayName("should return same test result as saved")
    void testSaveUserResultTest() {
        User user = new User(1, "last", "name");
        Integer countCorrectAnswer = 5;
        quizService.saveUserResultTest(user, countCorrectAnswer);

        Integer actual = quizService.getResultTest(user);

        assertThat(actual).isEqualTo(countCorrectAnswer);
    }

    @Test
    @DisplayName("when user has not result then return null")
    void whenUserHasNotThenReturnNull() {
        final QuizServiceImpl quizService = new QuizServiceImpl(quizDao);
        User user = new User(1, "last", "name");
        Integer countCorrectAnswer = 5;
        quizService.saveUserResultTest(user, countCorrectAnswer);

        User anotherUser = new User(1, "last", "name");
        Integer actual = quizService.getResultTest(anotherUser);

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("should return quizList")
    void testGetAllQuizzes() {
        final QuizServiceImpl quizService = new QuizServiceImpl(quizDao);
        List<Quiz> allQuizzes = quizService.getAllQuizzes();

        assertThat(allQuizzes).containsAll(quizList);
    }
}