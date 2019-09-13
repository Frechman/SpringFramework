package ru.gavrilov.libraryreact.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gavrilov.libraryreact.exceptions.BookNotFoundException;
import ru.gavrilov.libraryreact.exceptions.CommentNotFoundException;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Comment;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.repository.CommentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Методы сервиса работы с комментариями должны ")
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentRepository commentRepository;

    private Book testBook;

    @BeforeEach
    void init() {
        testBook = new Book("111-111-1", "Идиот", 1869, new Genre(), new Author());
    }

    @Test
    @DisplayName("добавлять комментарий.")
    void shouldAddComment() {
        Comment comment = new Comment("testText", testBook);
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.of(testBook));

        when(commentRepository.save(any())).thenReturn(comment);
        Comment actualComment = commentService.addComment(testBook.getIsbn(), "testText");
        assertThat(actualComment).isEqualTo(comment);
    }

    @Test
    @DisplayName("выбросить исключение при добавлении комментария, если isbn или content равно null.")
    void shouldThrowExceptionIfContentOrIsbnIsNullWhileAddComment() {
        assertThatThrownBy(() -> commentService.addComment(testBook.getIsbn(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Content or isbn book must not be null");

        assertThatThrownBy(() -> commentService.addComment(null, "text"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Content or isbn book must not be null");
    }

    @Test
    @DisplayName("выбросить исключение при добавлении комментария, если книга не найдена.")
    void shouldThrowExceptionIfBookNotFoundWhileAddComment() {
        when(bookService.findByIsbn(testBook.getIsbn())).thenThrow(new BookNotFoundException());

        assertThatThrownBy(() -> commentService.addComment(testBook.getIsbn(), "text"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found!");
    }

    @Test
    @DisplayName("удалять комментарий.")
    void shouldRemoveComment() {
        Comment comment = new Comment();
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.of(testBook));
        when(commentRepository.findByContentAndBook("text", testBook))
                .thenReturn(Optional.of(comment));

        boolean actual = commentService.removeComment(testBook.getIsbn(), "text");
        assertThat(actual).isTrue();

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    @DisplayName("выбросить исключение при удалении комментария, если комментарий не найден.")
    void shouldThrowExceptionIfCommentNotFoundWhileRemoveComment() {
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.of(testBook));
        when(commentRepository.findByContentAndBook("text", testBook))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.removeComment(testBook.getIsbn(), "text"))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("Comment not found!");
    }

    @Test
    @DisplayName("выбросить исключение при поиске комментариев для книги, если передали null.")
    void shouldThrowExceptionIfIsbnIsNullWhileFindAllCommentsByBook() {
        assertThatThrownBy(() -> commentService.findAllCommentsByBook(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Isbn must not be null");
    }

    @Test
    @DisplayName("выбросить исключение при поиске комментариев для книги, если книга не найдена.")
    void shouldThrowExceptionIfBookNotFoundWhileFindAllCommentsByBook() {
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.findAllCommentsByBook(testBook.getIsbn()))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found!");
    }

    @Test
    @DisplayName("выбросить исключение при поиске комментариев для книги, если передали null.")
    void findAllCommentsByBook() {
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.ofNullable(testBook));

        List<Comment> comments = Collections.singletonList(new Comment());
        when(commentRepository.findAllByBook(testBook)).thenReturn(comments);

        List<Comment> actualComments = commentService.findAllCommentsByBook(testBook.getIsbn());

        assertThat(actualComments).isEqualTo(comments);
    }

    @Test
    @DisplayName("находить комментарий по книге и содержимому.")
    void shouldFindCommentByContentAndBook() {
        Comment comment = new Comment("text", testBook);
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.of(testBook));
        when(commentRepository.findByContentAndBook("content", testBook))
                .thenReturn(Optional.of(comment));

        Optional<Comment> actual = commentService.findByContentAndBook(testBook.getIsbn(), "content");
        assertThat(actual).contains(comment);
    }

    @Test
    @DisplayName("выбросить исключение при поиске комментария, если книга не найдена.")
    void shouldThrowExceptionIfBookNotFoundWhileFindCommentByContentAndBook() {
        when(bookService.findByIsbn(testBook.getIsbn())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.findByContentAndBook(testBook.getIsbn(), "content"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found!");
    }

    @Test
    @DisplayName("выбросить исключение при поиске комментария, если isbn или content равно null.")
    void shouldThrowExceptionIfContentOrIsbnIsNullWhileFindCommentByContentAndBook() {
        assertThatThrownBy(() -> commentService.findByContentAndBook(testBook.getIsbn(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Content or Isbn must not be null");

        assertThatThrownBy(() -> commentService.findByContentAndBook(null, "text"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Content or Isbn must not be null");
    }

    @Test
    @DisplayName("возвращать список комментариев.")
    void shouldReturnCommentList() {
        Comment newComment = new Comment();
        newComment.setId(1L);
        Comment oldComment = new Comment();
        oldComment.setContent("999-888");
        when(commentRepository.findAll()).thenReturn(Arrays.asList(newComment, oldComment));

        assertThat(commentService.findAll()).containsOnly(oldComment, newComment);
    }
}