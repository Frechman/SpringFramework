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
        TypedQuery<Book> query =
                em.createQuery("SELECT b FROM Book b LEFT JOIN FETCH b.author LEFT JOIN FETCH b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<Book> findByIsbn(String isbn) {
        TypedQuery<Book> query =
                em.createQuery("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE lower(b.isbn) = :isbn",
                        Book.class);
        query.setParameter("isbn", isbn.toLowerCase());
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public Optional<Book> findByTitle(String title) {
        TypedQuery<Book> query =
                em.createQuery("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE lower(b.title) = :title",
                        Book.class);
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
        String sql = "SELECT b FROM Book b JOIN FETCH b.author a WHERE lower(a.lastName) LIKE :authorLastName";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("authorLastName", String.format("%%%s%%", authorLastName.toLowerCase()));
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Book> findAllByGenreName(String genreName) {
        String sql = "SELECT b FROM Book b JOIN FETCH b.genre g WHERE lower(g.name) LIKE :genreName";
        TypedQuery<Book> query = em.createQuery(sql, Book.class);
        query.setParameter("genreName", String.format("%%%s%%", genreName.toLowerCase()));
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("SELECT count(*) FROM Book b", Long.class).getSingleResult();
    }
}