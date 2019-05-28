package ru.gavrilov.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    @Transactional
    public Optional<Author> findByLastName(String lastName) {
        TypedQuery<Author> query =
                em.createQuery("SELECT a FROM Author a WHERE lower(a.lastName) LIKE :lastName", Author.class);
        query.setParameter("lastName", String.format("%%%s%%", lastName));
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public void save(Author author) {
        em.persist(author);
    }

    @Override
    @Transactional
    public void delete(Author author) {
        em.remove(author);
    }

    @Override
    @Transactional
    public Author update(Author author) {
        return em.merge(author);
    }
}