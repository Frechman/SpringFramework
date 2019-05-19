package ru.gavrilov.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Пример реализации транзакции без аннотации.
     */
    @Override
    public Optional<Comment> findByUuid(String uuid) {
        em.getTransaction().begin();
        Optional<Comment> comment = Optional.of(em.find(Comment.class, uuid));
        em.getTransaction().commit();
        return comment;
    }

    @Override
    @Transactional
    public boolean removeComment(String uuid) {
        Comment foundComment = em.find(Comment.class, uuid);
        if (foundComment != null) {
            em.remove(foundComment);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeComment(Comment comment) {
        return removeComment(comment.getUuid());
    }

    @Override
    @Transactional
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<Comment> findByContentAndBook(String content, String bookIsbn) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c " +
                "WHERE c.content LIKE :content AND c.book_isbn LIKE :book_isbn", Comment.class);
        query.setParameter("content", content);
        query.setParameter("book_isbn", bookIsbn);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public List<Comment> findAllByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.book_isbn = :isbn", Comment.class);
        query.setParameter("isbn", book.getIsbn());
        return query.getResultList();
    }

    @Override
    @Transactional
    public void createComment(Comment comment) {
        em.persist(comment);
    }
}
