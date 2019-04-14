package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.domain.Author;
import ru.gavrilov.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Author> findById(Long id) {
        if (id != null) {
            return authorRepository.findById(id);
        }
        throw new IllegalArgumentException("Id is not null!");
    }

    @Override
    public void save(Author author) {
        if (author != null) {
            authorRepository.save(author);
            return;
        }
        throw new IllegalArgumentException("Author is not null!");
    }

    @Override
    public void delete(Author author) {
        if (author != null) {
            authorRepository.delete(author);
            return;
        }
        throw new IllegalArgumentException("Author is not null!");
    }

    @Override
    public void update(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author is not null!");
        }
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());
        if (foundAuthor.isPresent()) {
            authorRepository.update(author);
        } else {
            authorRepository.save(author);
        }
    }
}
