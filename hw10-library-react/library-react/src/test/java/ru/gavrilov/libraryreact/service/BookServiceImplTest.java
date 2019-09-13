package ru.gavrilov.libraryreact.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.repository.BookRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Методы сервиса работы с книгами должны ")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("вернуть список всех книг.")
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
    @DisplayName("при поиске книгу по isbn бросать исключение, если передали null.")
    void shouldThrowExceptionIfNullWhileFindBookByIsbn() {
        assertThatThrownBy(() -> bookService.findByIsbn(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Isbn must not be null!");
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
    @DisplayName("при поиске книгу по названию бросать исключение, если передали null.")
    void shouldThrowExceptionIfNullWhileFindBookByTitle() {
        assertThatThrownBy(() -> bookService.findByTitle(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Title must not be null!");
    }

    @Test
    @DisplayName("при сохранении бросать исключение, если передали null.")
    void shouldThrowExceptionIfNullWhileSaveBook() {
        assertThatThrownBy(() -> bookService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book must not be null!");
    }

    @Test
    @DisplayName("сохранять книгу.")
    void shouldSaveBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);
        Book actualBook = bookService.save(book);
        verify(bookRepository, times(1)).save(book);
        assertThat(actualBook).isEqualTo(book);
    }

    @Test
    @DisplayName("при сохранении бросать исключение, если передали null.")
    void shouldThrowExceptionIfNullWhileUpdatedBook() {
        assertThatThrownBy(() -> bookService.update(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book must not be null!");
    }

    @Test
    @DisplayName("обновлять книгу.")
    void shouldUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book actualBook = bookService.update(1L, book);
        verify(bookRepository, times(1)).save(book);

        assertThat(actualBook).isEqualTo(book);
    }

    @Test
    @DisplayName("при удалении бросать исключение, если передали null.")
    void shouldThrowExceptionIfNullWhileDeleteBook() {
        assertThatThrownBy(() -> bookService.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book must not be null!");
    }

    @Test
    @DisplayName("удалять книгу.")
    void shouldDeleteBook() {
        Book expected = new Book();
        expected.setIsbn("111-111-1");
        bookService.delete(expected);
        verify(bookRepository, times(1)).delete(expected);
    }

    @Test
    @DisplayName("находить список книг по жанру.")
    void shouldFindBookByGenre() {
        Book expected = new Book();
        expected.setGenre(new Genre("Роман", "Роман - не человек"));

        when(bookRepository.findAllByGenreName("Роман")).thenReturn(Collections.singletonList(expected));
        assertThat(bookService.findAllByGenre("Роман").get(0))
                .extracting(Book::getGenre)
                .hasFieldOrPropertyWithValue("name", "Роман")
                .hasFieldOrPropertyWithValue("description", "Роман - не человек");
    }

    @Test
    @DisplayName("возвращать пустой список при поиске по жанру, если передали null.")
    void shouldEmptyCollectionIfNullWhileFindBookByGenre() {
        assertThat(bookService.findAllByGenre(null)).isEmpty();
    }

    @Test
    @DisplayName("находить список книг по автору.")
    void shouldFindBookByAuthor() {
        Book expected = new Book();
        expected.setAuthor(new Author("Фёдор", "Достоевский"));

        when(bookRepository.findAllByAuthorLastName("Достоевский")).thenReturn(Collections.singletonList(expected));
        assertThat(bookService.findAllByAuthor("Достоевский").get(0))
                .extracting(Book::getAuthor)
                .hasFieldOrPropertyWithValue("lastName", "Достоевский")
                .hasFieldOrPropertyWithValue("firstName", "Фёдор");
    }

    @Test
    @DisplayName("возвращать пустой список при поиске по автору, если передали null.")
    void shouldEmptyCollectionIfNullWhileFindBookByAuthor() {
        assertThat(bookService.findAllByAuthor(null)).isEmpty();
    }

    @Test
    @DisplayName("возвращать количество книг.")
    void shouldReturnCountOfBooks() {
        when(bookRepository.count()).thenReturn(1L);
        assertThat(bookService.count()).isEqualTo(1);
    }
}