package ru.gavrilov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gavrilov.model.Author;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Genre;
import ru.gavrilov.service.AuthorService;
import ru.gavrilov.service.BookService;
import ru.gavrilov.service.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;

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
    void testListBook() throws Exception {
        when(bookService.findAll()).thenReturn(bookList);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                //// TODO: 02.09.2019 draft
                .andExpect(content().string(containsString("123-456")))
                .andExpect(content().string(containsString("999-888")));

        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testAddBook() throws Exception {
        when(bookService.findAll()).thenReturn(bookList);
        when(authorService.findAll()).thenReturn(Arrays.asList(new Author("123", "123"), new Author("321", "321")));
        when(genreService.findAll()).thenReturn(Arrays.asList(
                new Genre("name1", "desc1"),
                new Genre("name2", "desc2"))
        );

        mockMvc.perform(get("/books/add")
                .content(objectMapper.writeValueAsBytes(bookList))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                //// TODO: 02.09.2019 draft
                .andExpect(content().string(containsString("123 123")))
                .andExpect(content().string(containsString("321 321")))
                .andExpect(content().string(containsString("name1")))
                .andExpect(content().string(containsString("name2")));

        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
        verifyNoMoreInteractions(authorService);
        verifyNoMoreInteractions(genreService);
    }

}