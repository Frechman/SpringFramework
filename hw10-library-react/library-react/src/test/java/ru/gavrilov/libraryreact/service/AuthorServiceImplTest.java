package ru.gavrilov.libraryreact.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gavrilov.libraryreact.exceptions.AuthorNotFoundException;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Методы сервиса работы с авторами должны ")
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;

    private List<Author> authorList;
    private Author author;
    private Author author2;

    @BeforeEach
    void init() {
        this.author = new Author("Александр", "Пушкин");
        this.author2 = new Author("Федор", "Достаевский");
        this.authorList = Arrays.asList(author, author2);
    }

    @Test
    @DisplayName("возвращать весь список авторов.")
    void whenFindAllThenReturnAuthorList() {
        when(authorRepository.findAll()).thenReturn(authorList);
        assertThat(authorService.findAll()).containsAll(authorList);
    }

    @Test
    @DisplayName("возвращать автора по id.")
    void shouldReturnCorrectAuthor() {
        final long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author2));
        assertThat(authorService.findById(authorId)).contains(author2);
    }

    @Test
    @DisplayName("выбросить исключение при поиске автора по id, если id = null.")
    void shouldThrowExceptionIfAuthorIdIsNull() {
        assertThatThrownBy(() -> authorService.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id must not be null!");
    }

    @Test
    @DisplayName("выбросить исключение при сохранении/удалении/обновлении автора, если author = null.")
    void shouldThrowExceptionIfAuthorIsNull() {
        assertThatThrownBy(() -> authorService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Author must not be null!");

        assertThatThrownBy(() -> authorService.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Author must not be null!");

        assertThatThrownBy(() -> authorService.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Author must not be null!");
    }

    @Test
    @DisplayName("сохранять автора.")
    void shouldSaveAuthor() {
        authorService.save(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("удалять автора.")
    void shouldDeleteAuthor() {
        authorService.delete(author);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    @DisplayName("обновлять автора.")
    void shouldUpdateAuthor() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        authorService.update(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("выбросить исключение, если автор не найден.")
    void shouldThrowException() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.update(author))
                .isInstanceOf(AuthorNotFoundException.class)
                .hasMessage("Author not found!");
    }
}