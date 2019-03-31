package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final JdbcOperations jdbc;

    @Autowired
    public GenreRepositoryJdbc(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

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
                new Object[]{id}, new GenreRepositoryJdbc.GenreMapper());
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

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new Genre(id, name, description);
        }
    }
}
