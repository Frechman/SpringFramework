package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.dao.AuthorRepository;
import ru.gavrilov.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        if (id != null) {
            return authorRepository.findById(id);
        }
        throw new IllegalArgumentException("Id is not null!");
    }

    @Override
    public void insert(Author author) {
        if (author != null) {
            authorRepository.insert(author);
            return;
        }
        throw new IllegalArgumentException("Author is not null!");
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            authorRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("Id is not null!");
    }

    @Override
    public void update(Long id, Author author) {
        Author updAuthor = id == null ? null : authorRepository.findById(id);
        if (updAuthor != null) {
            authorRepository.update(id, author);
        } else {
            authorRepository.insert(author);
        }
    }
}
