package ru.gavrilov.store;

import org.junit.jupiter.api.Test;
import ru.gavrilov.model.Quiz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizStoreImplTest {

    @Test
    public void getAllData() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test-application.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
        String pathFile = prop.getProperty("test.pathFile");
        QuizStoreImpl quizStore = new QuizStoreImpl(pathFile);
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("q1?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3?", 2, Arrays.asList("a1", "a2", "a3", "a4")));

        assertThat(quizStore.getAllData()).hasSize(3).containsAll(quizzes);
    }
}