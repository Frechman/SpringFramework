package ru.gavrilov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@DisplayName("Book controller's methods should ")
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
    @DisplayName("return model with 'bookList' attribute " +
            "and return view 'listBooks' when a GET request was made for '/books'")
    void testListBook() throws Exception {
        when(bookService.findAll()).thenReturn(bookList);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("books", bookList))
                .andExpect(view().name("listBooks"))
                .andExpect(content().string(containsString("123-456")))
                .andExpect(content().string(containsString("999-888")));

        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("return model with 'book','authorList','genreList' attributes" +
            "and return view 'addBook' when a GET request was made for '/books/add'")
    void testAddBook() throws Exception {
        List<Author> expectedAuthorList = Arrays.asList(new Author("123", "123"), new Author("321", "321"));
        when(authorService.findAll()).thenReturn(expectedAuthorList);
        List<Genre> expectedGenreList = Arrays.asList(
                new Genre("name1", "desc1"),
                new Genre("name2", "desc2"));
        when(genreService.findAll()).thenReturn(expectedGenreList);

        mockMvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("book", new Book()))
                .andExpect(model().attribute("authorList", expectedAuthorList))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(view().name("addBook"))
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