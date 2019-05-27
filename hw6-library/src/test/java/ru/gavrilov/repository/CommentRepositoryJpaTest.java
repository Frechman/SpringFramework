package ru.gavrilov.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Методы сервиса для работы с комментариями должны ")
@ComponentScan({"ru.gavrilov.repository"})
class CommentRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    private Book book;
    private Comment comment;

    @BeforeEach
    void init() {
        book = new Book();
        book.setIsbn("999-888");
        comment = em.persist(new Comment("test", book));
    }

    @Test
    @DisplayName("получать все комментарии.")
    void findAll() {
        assertThat(commentRepository.findAll()).hasSize(1).contains(comment);
    }

    @Test
    @DisplayName("получать все комментарии, которые есть у книги.")
    void findAllByBook() {
        assertThat(commentRepository.findAllByBook(book)).hasSize(1).contains(comment);
    }

    @Test
    @DisplayName("оставлять комментарии к книгам.")
    void createComment() {
        commentRepository.createComment(comment);

        assertThat(commentRepository.findAll().get(0))
                .extracting(Comment::getContent)
                .isEqualTo("test");
    }

    @Test
    @DisplayName("получать комментарий по uuid.")
    void findByUuid() {
        Comment test = em.persist(comment);

        assertThat(test).isEqualTo(em.find(Comment.class, test.getUuid()));
    }

    @Test
    @DisplayName("получать комментарий по содержимому и книге.")
    void findByContentAndBook() {
        Comment test = em.persist(comment);

        Optional<Comment> actual = commentRepository.findByContentAndBook("test", "999-888");
        assertThat(actual.get()).isEqualTo(test);
    }

    @Test
    @DisplayName("удалять комментарии.")
    void removeComment() {
        Comment test = em.persist(comment);
        assertThat(commentRepository.findAll()).hasSize(1);

        assertThat(commentRepository.removeComment(test)).isTrue();
        assertThat(commentRepository.findAll()).isEmpty();
    }
}