package ru.gavrilov.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.gavrilov.model.Quiz;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class QuizStoreImplTest {

    @MockBean
    MessageSource messageSource;

    @Test
    void getAllEnglishData() {
        when(messageSource.getMessage("test.pathFile", null, Locale.getDefault()))
                .thenReturn("/test-questions_en.csv");
        QuizStoreImpl quizStore = new QuizStoreImpl(messageSource);
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("q1?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q2?", 1, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q3?", 2, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q4?", 2, Arrays.asList("a1", "a2", "a3", "a4")),
                new Quiz("q5?", 2, Arrays.asList("a1", "a2", "a3", "a4")));

        assertThat(quizStore.getAllData()).hasSize(5).containsAll(quizzes);
    }

    @Test
    void getAllRussianData() {
        when(messageSource.getMessage("test.pathFile", null, Locale.getDefault()))
                .thenReturn("/test-questions_ru.csv");
        QuizStoreImpl quizStore = new QuizStoreImpl(messageSource);
        List<Quiz> quizzes = Arrays.asList(
                new Quiz("в1?", 1, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в2?", 1, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в3?", 2, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в4?", 2, Arrays.asList("о1", "о2", "о3", "о4")),
                new Quiz("в5?", 2, Arrays.asList("о1", "о2", "о3", "о4")));

        assertThat(quizStore.getAllData()).hasSize(5).containsAll(quizzes);
    }
}
