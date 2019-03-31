package ru.gavrilov.service;

import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;

@JdbcTest
@ActiveProfiles("test")
@DisplayName("Методы сервиса работы с книгами ")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @Ignore
    @DisplayName("должен вернуть список всех книг")
    void shouldReturnAllBooks() {
        bookService.findAll();
    }
}