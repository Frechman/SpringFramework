package ru.gavrilov.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<Book> findByIsbn(String isbn) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public void save(Book book) {
        em.persist(book);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        em.persist(book);
    }

    @Override
    @Transactional
    public List<Book> findAllByLastNameAuthor(String authorLastName) {
        String sql = "SELECT b FROM Book b INNER JOIN Author a ON b.author_id = a.id WHERE a.last_name ILIKE %:authorLastName%";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("authorLastName", authorLastName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Book> findAllByGenreName(String genreName) {
        String sql = "SELECT b FROM Book b INNER JOIN Genre g ON b.genre_id = g.id WHERE g.name ILIKE %:genreName%";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("genreName", genreName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Book> findAllByContentComment(String content) {
        String sql = "SELECT b FROM Book b INNER JOIN Comment c ON b.comment_id = c.id WHERE c.content ILIKE %:content%";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("content", content);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {
        return em.createQuery("SELECT count(b) FROM Book b", Integer.class).getSingleResult();
    }
}