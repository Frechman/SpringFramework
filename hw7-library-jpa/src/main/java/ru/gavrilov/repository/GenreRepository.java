package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
