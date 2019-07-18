package ru.gavrilov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.gavrilov.model.Author;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Genre;
import ru.gavrilov.repository.BookRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Методы сервиса работы с книгами должны ")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("найти все книги.")
    void shouldFindAllBooks() {
        Book newBook = new Book();
        newBook.setIsbn("123-456");
        when(bookRepository.save(newBook)).thenReturn(newBook);
        bookService.save(newBook);
        Book oldBook = new Book();
        oldBook.setIsbn("999-888");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(newBook, oldBook));

        assertThat(bookService.findAll()).containsOnly(oldBook, newBook);
    }

    @Test
    @DisplayName("находить книгу по isbn.")
    void shouldFindBookByIsbn() {
        Book expected = new Book();
        expected.setIsbn("999-888");
        when(bookRepository.findByIsbn("999-888")).thenReturn(Optional.of(expected));
        Book book = bookService.findByIsbn("999-888").orElseGet(Book::new);
        assertThat(book).isEqualTo(expected);
    }

    @Test
    @DisplayName("находить книгу по названию.")
    void shouldFindBookByTitle() {
        Book expected = new Book();
        expected.setTitle("Идиот");
        when(bookRepository.findByTitle("Идиот")).thenReturn(Optional.of(expected));
        Optional<Book> actual = bookService.findByTitle("Идиот");
        assertThat(actual.orElseGet(Book::new))
                .isEqualTo(expected)
                .hasFieldOrPropertyWithValue("title", "Идиот");
    }

    @Test
    @DisplayName("при сохранении бросать исключение, если передали null.")
    void shouldSaveBook() {
        assertThatThrownBy(() -> bookService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book must not be null!");
    }

    @Test
    @DisplayName("при удалении бросать исключение, если передали null.")
    void shouldDeleteBook() {
        assertThatThrownBy(() -> bookService.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book must not be null!");
    }

    @Test
    @DisplayName("находить список книг по жанру.")
    void shouldFindBookByGenre() {
        Book expected = new Book();
        expected.setGenre(new Genre("Роман", "description"));

        when(bookRepository.findAllByGenreName("Роман")).thenReturn(Collections.singletonList(expected));
        assertThat(bookService.findAllByGenre("Роман").get(0))
                .extracting(Book::getGenre)
                .hasFieldOrPropertyWithValue("name", "Роман");
    }

    @Test
    @DisplayName("находить список книг по автору.")
    void shouldFindBookByAuthor() {
        Book expected = new Book();
        expected.setAuthor(new Author("Фёдор", "Достоевский"));

        when(bookRepository.findAllByAuthorLastName("Достоевский")).thenReturn(Collections.singletonList(expected));
        assertThat(bookService.findAllByAuthor("Достоевский").get(0))
                .extracting(Book::getAuthor)
                .hasFieldOrPropertyWithValue("lastName", "Достоевский");
    }

    @Test
    @DisplayName("возвращать количество книг.")
    void shouldReturnCountOfBooks() {
        when(bookRepository.count()).thenReturn(1L);
        assertThat(bookService.count()).isEqualTo(1);
    }

}