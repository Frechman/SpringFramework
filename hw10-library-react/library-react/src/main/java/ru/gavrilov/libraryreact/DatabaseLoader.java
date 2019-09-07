package ru.gavrilov.libraryreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.repository.AuthorRepository;
import ru.gavrilov.libraryreact.repository.BookRepository;
import ru.gavrilov.libraryreact.repository.GenreRepository;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public DatabaseLoader(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Genre genre1 = new Genre("Роман", "Роман - не человек");
        Genre genre2 = new Genre("Поэзия", "Поэзия");
        this.genreRepository.saveAll(Arrays.asList(genre1, genre2));
        Author author1 = new Author("Федор", "Достоевский");
        Author author2 = new Author("Александр", "Пушкин");
        Author author3 = new Author("Михаил", "Булгаков");
        this.authorRepository.saveAll(Arrays.asList(author1, author2, author3));
        this.bookRepository.saveAll(Arrays.asList(
                new Book("111-111", "Идиот", 1869, genre1, author1),
                new Book("222-222-2", "Мастер и Маргарита", 1937, genre1, author3),
                new Book("333-333-3", "Евгений Онегин", 1831, genre1, author2),
                new Book("444-444-4", "Медный всадник", 1837, genre2, author2)
        ));
    }
}
