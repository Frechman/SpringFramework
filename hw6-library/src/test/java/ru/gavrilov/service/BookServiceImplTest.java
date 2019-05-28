package ru.gavrilov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.domain.Book;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Методы сервиса работы с книгами должны ")
@Transactional(propagation = Propagation.REQUIRES_NEW)
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("найти все книги.")
    void shouldFindAllBooks() {
        Book newBook = new Book();
        newBook.setIsbn("123-456");
        bookService.save(newBook);
        Book oldBook = new Book();
        oldBook.setIsbn("999-888");

        assertThat(bookService.findAll()).containsOnly(oldBook, newBook);
    }

    @Test
    @DisplayName("находить книгу по isbn.")
    void shouldFindBookByIsbn() {
        Book book = bookService.findByIsbn("999-888").get();
        Book expected = new Book();
        expected.setIsbn("999-888");
        assertThat(book).isEqualTo(expected);
    }

    @Test
    @DisplayName("находить книгу по названию.")
    void shouldFindBookByTitle() {
        Optional<Book> byIsbn = bookService.findByTitle("Идиот");
        Book expected = new Book();
        expected.setIsbn("999-888");
        assertThat(byIsbn.get())
                .isEqualTo(expected)
                .hasFieldOrPropertyWithValue("title", "Идиот");
    }

    @Test
    @DisplayName("сохранять книгу.")
    void shouldSaveBook() {
        Book newBook = new Book();
        newBook.setIsbn("123-456");

        assertThat(bookService.findAll())
                .hasSize(1)
                .doesNotContain(newBook);

        bookService.save(newBook);

        assertThat(bookService.findAll())
                .hasSize(2)
                .contains(newBook);
    }

    @Test
    @DisplayName("удалять книгу.")
    void shouldDeleteBook() {
        Book oldBook = bookService.findByIsbn("999-888").get();

        assertThat(bookService.findAll())
                .hasSize(1);

        bookService.delete(oldBook);

        assertThat(bookService.findAll()).isEmpty();
    }

    @Test
    @DisplayName("находить список книг по жанру.")
    void shouldFindBookByGenre() {
        assertThat(bookService.findAllByGenre("Роман").get(0))
                .extracting(Book::getGenre)
                .hasFieldOrPropertyWithValue("name", "Роман");
    }

    @Test
    @DisplayName("находить список книг по автору.")
    void shouldFindBookByAuthor() {
        assertThat(bookService.findAllByAuthor("Достоевский").get(0))
                .extracting(Book::getAuthor)
                .hasFieldOrPropertyWithValue("lastName", "Достоевский");
    }

    @Test
    @DisplayName("возвращать количество книг.")
    void shouldReturnCountOfBooks() {
        assertThat(bookService.count()).isEqualTo(1);
    }

}