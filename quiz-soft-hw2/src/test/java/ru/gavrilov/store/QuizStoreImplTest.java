package ru.gavrilov.store;

import org.junit.jupiter.api.Test;
import ru.gavrilov.model.Quiz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuizStoreImplTest {

    @Test
    public void getAllEnglishData() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test-application.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
        String pathFile = prop.getProperty("test.pathFile");
        String locale = prop.getProperty("test.locale");
        QuizStoreImpl quizStore = new QuizStoreImpl(pathFile, locale);
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("q1?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3?", 2, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q4?", 2, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q5?", 2, Arrays.asList("a1", "a2", "a3", "a4")));

        assertThat(quizStore.getAllData()).hasSize(5).containsAll(quizzes);
    }

    @Test
    public void getAllRussianData() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test-application.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
        String pathFile = prop.getProperty("test.pathFile");
        String locale = "ru";
        QuizStoreImpl quizStore = new QuizStoreImpl(pathFile, locale);
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("в1?", 1, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в2?", 1, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в3?", 2, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в4?", 2, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в5?", 2, Arrays.asList("о1", "о2", "о3", "о4")));

        assertThat(quizStore.getAllData()).hasSize(5).containsAll(quizzes);
    }

    @Test
    public void getException() {
        assertThrows(NullPointerException.class, () -> new QuizStoreImpl("errorpath", "ru"));
    }
}