package ru.gavrilov.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gavrilov.model.Quiz;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration
class QuizStoreImplTest {

    @Value("${test.pathFile}")
    private String pathFile;
    @Value("${test.locale}")
    private String locale;

    @Test
    void getAllEnglishData() throws IOException {
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
    void getAllRussianData() throws IOException {
        locale = "ru";
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
    void getException() {
        assertThrows(NullPointerException.class, () -> new QuizStoreImpl("errorpath", "ru"));
    }

    @TestConfiguration
    static class QuizStoreImplTestConfiguration {

        @Bean
        static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}