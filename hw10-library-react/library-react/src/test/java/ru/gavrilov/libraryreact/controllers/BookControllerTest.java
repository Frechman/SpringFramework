package ru.gavrilov.libraryreact.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gavrilov.libraryreact.dto.BookDto;
import ru.gavrilov.libraryreact.mappers.AuthorMapper;
import ru.gavrilov.libraryreact.mappers.BookMapper;
import ru.gavrilov.libraryreact.mappers.GenreMapper;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BookController.class, BookMapper.class, AuthorMapper.class, GenreMapper.class})
@DisplayName("Book controller's methods should ")
class BookControllerTest {

    private static final String API = "/api/v1";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    private Book book;
    private Book book2;
    private List<Book> bookList;

    private Book generateTestBook() {
        Book book = new Book();
        book.setId(new Random().nextLong());
        book.setTitle(String.format("title %d", new Random().nextLong()));
        book.setIsbn(String.valueOf(new Random().nextLong()));
        book.setPublishYear(new Random().nextLong());
        book.setAuthor(new Author("authorName", "authorLasName"));
        book.setGenre(new Genre("genreName", "genreDesc"));
        return book;
    }

    @BeforeEach
    void setUp() {
        book = generateTestBook();
        book.setIsbn("123-456");
        book2 = generateTestBook();
        book2.setIsbn("999-888");
        bookList = Arrays.asList(book, book2);
    }

    @Test
    @DisplayName("return json array of book when a GET request was made for '/books'")
    void tesGetListBook() throws Exception {
        when(bookService.findAll()).thenReturn(bookList);

        mockMvc.perform(get(API + "/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(book.getId())))
                .andExpect(jsonPath("$[0].isbn", is(book.getIsbn())))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())))
                .andExpect(jsonPath("$[0].publishYear", is(book.getPublishYear())))
                .andExpect(jsonPath("$[0].author.firstName", is(book.getAuthor().getFirstName())))
                .andExpect(jsonPath("$[0].author.lastName", is(book.getAuthor().getLastName())))
                .andExpect(jsonPath("$[0].genre.name", is(book.getGenre().getName())))
                .andExpect(jsonPath("$[0].genre.description", is(book.getGenre().getDescription())))
                .andExpect(jsonPath("$[1].id", is(book2.getId())))
                .andExpect(jsonPath("$[1].isbn", is(book2.getIsbn())))
                .andExpect(jsonPath("$[1].title", is(book2.getTitle())))
                .andExpect(jsonPath("$[1].publishYear", is(book2.getPublishYear())))
                .andExpect(jsonPath("$[1].author.firstName", is(book2.getAuthor().getFirstName())))
                .andExpect(jsonPath("$[1].author.lastName", is(book2.getAuthor().getLastName())))
                .andExpect(jsonPath("$[1].genre.name", is(book2.getGenre().getName())))
                .andExpect(jsonPath("$[1].genre.description", is(book2.getGenre().getDescription())));

        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("return BookDto 'attributes' when a GET request was made for '/books/attributes'")
    void testGetAttributes() throws Exception {
        BookDto bookDto = new BookDto();
        mockMvc.perform(get(API + "/books/attributes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookDto.getId())))
                .andExpect(jsonPath("$.isbn", is(bookDto.getIsbn())))
                .andExpect(jsonPath("$.title", is(bookDto.getTitle())))
                .andExpect(jsonPath("$.publishYear", is(bookDto.getPublishYear())))
                .andExpect(jsonPath("$.author", is(bookDto.getAuthor())))
                .andExpect(jsonPath("$.genre", is(bookDto.getGenre())));
    }

    @Test
    @DisplayName("added (saved) the book using 'bookService' when a POST request was made for '/books/add'")
    void testPostAddBook() throws Exception {
        when(bookService.save(book)).thenReturn(book);

        BookDto bookDto = bookMapper.toBookDto(book);
        mockMvc.perform(
                post(API + "/books/add")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(book.getId())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.publishYear", is(book.getPublishYear())))
                .andExpect(jsonPath("$.author.id", is(book.getAuthor().getId())))
                .andExpect(jsonPath("$.author.firstName", is(book.getAuthor().getFirstName())))
                .andExpect(jsonPath("$.author.lastName", is(book.getAuthor().getLastName())))
                .andExpect(jsonPath("$.genre.id", is(book.getGenre().getId())))
                .andExpect(jsonPath("$.genre.name", is(book.getGenre().getName())))
                .andExpect(jsonPath("$.genre.description", is(book.getGenre().getDescription())));

        verify(bookService, times(1)).save(book);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("return the book when a GET request was made for '/books/{isbn}'")
    void testGetBook() throws Exception {
        when(bookService.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

        mockMvc.perform(get(API + "/books/{isbn}", book.getIsbn()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.publishYear", is(book.getPublishYear())))
                .andExpect(jsonPath("$.author.id", is(book.getAuthor().getId())))
                .andExpect(jsonPath("$.author.firstName", is(book.getAuthor().getFirstName())))
                .andExpect(jsonPath("$.author.lastName", is(book.getAuthor().getLastName())))
                .andExpect(jsonPath("$.genre.id", is(book.getGenre().getId())))
                .andExpect(jsonPath("$.genre.name", is(book.getGenre().getName())))
                .andExpect(jsonPath("$.genre.description", is(book.getGenre().getDescription())));

        verify(bookService, times(1)).findByIsbn(book.getIsbn());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("throw BookNotFoundException " +
            "if method 'findByIsbn' return Optional.empty when a GET request was made for '/books/{isbn}'")
    void testGetBookThrowException() throws Exception {
        when(bookService.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());

        mockMvc.perform(get(API + "/books/{isbn}", book.getIsbn()))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findByIsbn(book.getIsbn());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("update the book using 'bookService' when a PUT request was made for '/books/update'")
    void testPutUpdateBook() throws Exception {
        when(bookService.update(book2.getId(), book2)).thenReturn(book2);

        mockMvc.perform(
                put(API + "/books/update")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(bookMapper.toBookDto(book2))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(book2.getId())))
                .andExpect(jsonPath("$.isbn", is(book2.getIsbn())))
                .andExpect(jsonPath("$.title", is(book2.getTitle())))
                .andExpect(jsonPath("$.publishYear", is(book2.getPublishYear())))
                .andExpect(jsonPath("$.author.id", is(book2.getAuthor().getId())))
                .andExpect(jsonPath("$.author.firstName", is(book2.getAuthor().getFirstName())))
                .andExpect(jsonPath("$.author.lastName", is(book2.getAuthor().getLastName())))
                .andExpect(jsonPath("$.genre.id", is(book2.getGenre().getId())))
                .andExpect(jsonPath("$.genre.name", is(book2.getGenre().getName())))
                .andExpect(jsonPath("$.genre.description", is(book2.getGenre().getDescription())));

        verify(bookService, times(1)).update(book2.getId(), book2);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("delete the book when a DELETE request was made for '/books/delete'")
    void testDeleteBook() throws Exception {
        when(bookService.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

        doNothing().when(bookService).delete(book);
        mockMvc.perform(
                delete(API + "/books/delete")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(book.getIsbn()))
                .andExpect(status().isAccepted());

        verify(bookService, times(1)).findByIsbn(book.getIsbn());
        verify(bookService, times(1)).delete(book);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("throw BookNotFoundException " +
            "if method 'findByIsbn' return Optional.empty when a DELETE request was made for '/books/delete'")
    void testDeleteBookThrowException() throws Exception {
        when(bookService.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());

        mockMvc.perform(
                delete(API + "/books/delete")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(book.getIsbn()))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findByIsbn(book.getIsbn());
        verify(bookService, never()).delete(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }
}