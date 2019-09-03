package ru.gavrilov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class AppTest {

    @Test
    @DisplayName("Тест загрузки контекта")
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
