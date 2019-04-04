package ru.gavrilov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.gavrilov.model.Book;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@ContextConfiguration(classes = BookRepositoryJdbc.class)
@DisplayName("Методы репозитория книг должны ")
class BookRepositoryJdbcTest {


    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("возращать все книги из БД>")
    void shouldReturnAllBooks() {
        assertThat(repository.findAll())
                .containsAll(Collections.singleton(new Book("999-888", "Идиот", 1869L, 1L, 1L)));
    }

    @Test
    @DisplayName("возвращать корректную книгу по isbn")
    void shouldReturnCorrectBookByIsbn() {
        assertThat(repository.findByIsbn("999-888"))
                .isEqualTo(new Book("999-888", "Идиот", 1869L, 1L, 1L));
    }

    @Test
    @DisplayName("возращать колличество книг в БД")
    void shouldReturnCountBooks() {
        assertThat(repository.count())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("сохранять книгу в БД")
    void shouldInsertBookInDb() {
        Book testBook = new Book("111", "test", 100L, 1L, 1L);
        repository.insert(testBook);
        assertThat(repository.findByIsbn(testBook.getIsbn()))
                .isEqualTo(testBook);
    }

    @Test
    @DisplayName("удалять книгу из БД по isbn")
    void shouldDeleteBookByIsbn() {
        Book testBook = new Book("222", "test2", 1010L, 2L, 1L);
        repository.insert(testBook);
        repository.deleteByIsbn("999-888");
        assertThat(repository.findAll())
                .hasSize(1)
                .containsOnly(testBook);
    }

    @Test
    @DisplayName("возвращать список книг определенного автора")
    void shouldReturnBooksByAuthor() {
        Book testBook = new Book("222", "test2", 1010L, 1L, 1L);
        repository.insert(testBook);

        Book testBook2 = new Book("999-888", "Идиот", 1869L, 1L, 1L);
        assertThat(repository.findAllByAuthor("Достоевский"))
                .hasSize(2)
                .containsAll(Arrays.asList(testBook2, testBook));
    }

    @Test
    @DisplayName("возвращать список книг выбранного жанра")
    void shouldReturnBooksByGenre() {
        Book otherBook = new Book("222", "test2", 1010L, 2L, 1L);
        repository.insert(otherBook);

        assertThat(repository.findAllByGenre("Поэзия"))
                .hasSize(1)
                .containsOnly(otherBook);
    }
}