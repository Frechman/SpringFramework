package ru.gavrilov.libraryreact.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Методы сервиса для работы с комментариями должны ")
@ComponentScan({"ru.gavrilov.libraryreact.repository"})
class CommentRepositoryJpaTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    private Book book;
    private Comment comment;

    @BeforeEach
    void init() {
        book = em.find(Book.class, 1L);
        comment = em.persist(new Comment("test", book));
    }

    @Test
    @DisplayName("получать все комментарии.")
    void shouldFindAllComments() {
        assertThat(commentRepository.findAll()).hasSize(1).contains(comment);
    }

    @Test
    @DisplayName("получать все комментарии, которые есть у книги.")
    void shouldFindAllCommentsByBook() {
        assertThat(commentRepository.findAllByBook(book)).hasSize(1).contains(comment);
    }

    @Test
    @DisplayName("оставлять комментарии к книгам.")
    void shouldAddComment() {
        assertThat(commentRepository.findAll().get(0))
                .extracting(Comment::getContent)
                .isEqualTo("test");
    }

    @Test
    @DisplayName("получать комментарий по id.")
    void shouldFindCommentById() {
        assertThat(comment).isEqualTo(em.find(Comment.class, comment.getId()));
    }

    @Test
    @DisplayName("получать комментарий по содержимому и книге.")
    void shouldFindCommentByContentAndBook() {
        Optional<Comment> actual = commentRepository.findByContentAndBook("test", book);
        assertThat(actual.orElse(null)).isEqualTo(comment);
    }

    @Test
    @DisplayName("удалять комментарии.")
    void shouldRemoveComment() {
        assertThat(commentRepository.findAll()).hasSize(1);

        commentRepository.delete(comment);
        assertThat(commentRepository.findAll()).isEmpty();
    }
}