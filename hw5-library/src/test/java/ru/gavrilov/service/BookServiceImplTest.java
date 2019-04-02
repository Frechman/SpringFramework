package ru.gavrilov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.gavrilov.dao.BookRepository;
import ru.gavrilov.model.Book;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@JdbcTest
@ActiveProfiles("test")
@ContextConfiguration(classes = BookServiceImpl.class)
@DisplayName("Методы сервиса работы с книгами должны ")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository repositoryJdbc;

    private List<Book> books;

    @BeforeEach
    void setUp() {
        this.books = Arrays.asList(
                new Book("999", "book", 1L, 1L, 1L),
                new Book("222", "book", 2L, 2L, 2L));
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
        Book book = new Book("999", "book", 1L, 1L, 1L);
        when(repositoryJdbc.findByIsbn("999")).thenReturn(book);
        assertThat(bookService.findByIsbn("999"))
                .isEqualTo(books.get(0));
    }

    @Test
    @DisplayName("вызывать метод repositoryJdbc с нужными параметрами. Метод: insertBook")
    void shouldExecuteServiceMethodForInsertBook() {
        Book testBook = new Book("999", "test", 1999L, 1L, 1L);
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