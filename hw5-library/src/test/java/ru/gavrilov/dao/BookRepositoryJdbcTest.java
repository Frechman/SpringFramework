package ru.gavrilov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gavrilov.config.TestContextConfig;
import ru.gavrilov.model.Author;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Genre;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = TestContextConfig.class)
@DisplayName("Методы репозитория книг должны ")
class BookRepositoryJdbcTest {

    private final BookRepository repository;
    private Book bookExistInDb = new Book("999-888", "Идиот", 1869L,
            new Genre(1, "Роман", "Роман - не человек"),
            new Author(1, "Федор", "Достоевский"));
    private Book testBook = new Book("222", "test2", 1010L,
            new Genre(2, "Поэзия", "Поэзия"),
            new Author(1, "Федор", "Достоевский"));

    @Autowired
    BookRepositoryJdbcTest(BookRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("возращать все книги из БД")
    void shouldReturnAllBooks() {
        assertThat(repository.findAll())
                .containsAll(Collections.singleton(bookExistInDb));
    }

    @Test
    @DisplayName("возвращать корректную книгу по isbn")
    void shouldReturnCorrectBookByIsbn() {
        assertThat(repository.findByIsbn("999-888"))
                .isEqualTo(bookExistInDb);
        assertThat(repository.findAll()).hasSize(1);
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
        Book testBook = new Book("111", "test", 100L,
                this.testBook.getGenre(), this.testBook.getAuthor());
        repository.insert(testBook);
        assertThat(repository.findByIsbn(testBook.getIsbn()))
                .isEqualTo(testBook);
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("удалять книгу из БД по isbn")
    void shouldDeleteBookByIsbn() {
        repository.insert(testBook);
        repository.deleteByIsbn("999-888");
        assertThat(repository.findAll())
                .hasSize(1)
                .containsOnly(testBook);
    }

    @Test
    @DisplayName("возвращать список книг определенного автора")
    void shouldReturnBooksByAuthor() {
        repository.insert(testBook);

        assertThat(repository.findAllByAuthor("Достоевский"))
                .hasSize(2)
                .containsAll(Arrays.asList(bookExistInDb, testBook));
    }

    @Test
    @DisplayName("возвращать список книг выбранного жанра")
    void shouldReturnBooksByGenre() {
        repository.insert(testBook);

        assertThat(repository.findAllByGenre("Поэзия"))
                .hasSize(1)
                .containsOnly(testBook);
    }
}