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
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE lower(b.isbn) = :isbn", Book.class);
        query.setParameter("isbn", isbn.toLowerCase());
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public Optional<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE lower(b.title) = :title", Book.class);
        query.setParameter("title", title.toLowerCase());
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
        em.remove(em.contains(book) ? book : em.merge(book));
    }

    @Override
    @Transactional
    public List<Book> findAllByLastNameAuthor(String authorLastName) {
        String sql = "SELECT b FROM Book b INNER JOIN Author a ON b.author.id = a.id WHERE lower(a.lastName) LIKE :authorLastName";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("authorLastName", String.format("%%%s%%", authorLastName.toLowerCase()));
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Book> findAllByGenreName(String genreName) {
        String sql = "SELECT b FROM Book b INNER JOIN Genre g ON b.genre.id = g.id WHERE lower(g.name) LIKE :genreName";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("genreName", String.format("%%%s%%", genreName.toLowerCase()));
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Book> findAllByContentComment(String content) {
        String sql = "SELECT b FROM Book b INNER JOIN Comment c ON b.comment.id = c.id WHERE lower(c.content) LIKE :content";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("content", String.format("%%%s%%", content.toLowerCase()));
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("SELECT count(b) FROM Book b", Long.class).getSingleResult();
    }
}