package ru.gavrilov.store;

import org.junit.Test;
import ru.gavrilov.model.Quiz;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizStoreImplTest {

    @Test
    public void getAllData() throws IOException {
        QuizStoreImpl quizStore = new QuizStoreImpl("test-questions.csv");
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("q1?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3?", 2, Arrays.asList("a1", "a2", "a3", "a4")));
        assertThat(quizStore.getAllData())
                .hasSize(3)
                .containsAll(quizzes);
    }
}