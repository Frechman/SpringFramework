package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.gavrilov.mapper.GenreMapper;
import ru.gavrilov.model.Genre;

import java.util.List;

/**
 * Реализация с JdbcOperations.
 */
@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final JdbcOperations jdbc;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreRepositoryJdbc(JdbcOperations jdbc, GenreMapper genreMapper) {
        this.jdbc = jdbc;
        this.genreMapper = genreMapper;
    }

    /**
     * RowMapper с помощью lambda выражения.
     */
    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT * FROM genre", (rs, i) ->
                new Genre(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"))
        );
    }

    @Override
    public Genre findById(Long id) {
        return jdbc.queryForObject("SELECT * FROM genre WHERE id = ?",
                new Object[]{id}, genreMapper);
    }

    @Override
    public void insert(Genre genre) {
        String sql = "INSERT INTO genre (id, name, description) VALUES (?, ?, ?)";
        jdbc.update(sql, genre.getId(), genre.getName(), genre.getDescription());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM genre WHERE id = ?", id);
    }

    @Override
    public void update(Long id, Genre genre) {
        jdbc.update("UPDATE genre SET description = ?, name = ? WHERE id = ?",
                genre.getDescription(), genre.getName(), genre.getId());
    }
}
