package ru.gavrilov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.gavrilov.config.TestContextConfig;
import ru.gavrilov.dao.BookRepository;
import ru.gavrilov.model.Author;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@JdbcTest
@ContextConfiguration(classes = TestContextConfig.class)
@DisplayName("Методы сервиса работы с книгами должны ")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository repositoryJdbc;

    private List<Book> books;
    private Book testBook;
    private Book testBook2;

    @BeforeEach
    void setUp() {
        testBook = new Book("222", "book", 2L,
                new Genre(2, "Поэзия", "Поэзия"),
                new Author(2, "a", "a"));
        testBook2 = new Book("999", "book", 1L,
                new Genre(1, "Роман", "Роман - не человек"),
                new Author(1, "Достоевский", "Федор"));


        this.books = Arrays.asList(testBook, testBook2);
    }

    @Test
    @DisplayName("возвращать список всех книг")
    void shouldReturnAllBooks() {
        when(repositoryJdbc.findAll()).thenReturn(books);
        assertThat(bookService.findAll())
                .containsAll(books);
    }

    @Test
    @DisplayName("возвращать правильную книгу по isbn")
    void shouldReturnRightBookByIsbn() {
        when(repositoryJdbc.findByIsbn("999")).thenReturn(testBook);
        assertThat(bookService.findByIsbn("999"))
                .isEqualTo(books.get(0));
    }

    @Test
    @DisplayName("вызывать метод repositoryJdbc с нужными параметрами. Метод: insertBook")
    void shouldExecuteServiceMethodForInsertBook() {
        bookService.insert(testBook);
        verify(repositoryJdbc, times(1)).insert(testBook);
    }

    @Test
    @DisplayName("вызывать метод repositoryJdbc с нужными параметрами. Метод: deleteByIsbn")
    void shouldExecuteServiceMethodForDeleteByIsbn() {
        bookService.deleteByIsbn("999");
        verify(repositoryJdbc, times(1)).deleteByIsbn("999");
    }

    @Test
    @DisplayName("возвращать список всех книг по жанру")
    void shouldReturnAllBooksByGenre() {
        when(repositoryJdbc.findAllByGenre(any())).thenReturn(books.subList(1, 2));
        assertThat(bookService.findAllByGenre("genre"))
                .containsOnly(books.get(1));
    }

    @Test
    @DisplayName("возвращать список всех книг по фамилии автора")
    void shouldReturnAllBooksByAuthor() {
        when(repositoryJdbc.findAllByAuthor(any())).thenReturn(books.subList(0, 1));
        assertThat(bookService.findAllByAuthor("firstName"))
                .containsOnly(books.get(0));
    }

    @Test
    @DisplayName("возвращать колличество всех книг")
    void shouldReturnCountAllBooksEqualsThree() {
        when(repositoryJdbc.count()).thenReturn(2);
        assertThat(bookService.count())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("бросать исключение если isbn книги не ввели")
    void shouldThrowFoundExceptionIfGreetingNotExists() {
        given(repositoryJdbc.findByIsbn(any())).willReturn(null);
        assertThatThrownBy(() -> bookService.findByIsbn(null)).isInstanceOf(IllegalArgumentException.class);
    }
}