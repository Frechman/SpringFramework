package ru.gavrilov.libraryreact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.libraryreact.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
