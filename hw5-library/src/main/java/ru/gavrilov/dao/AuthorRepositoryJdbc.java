package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Author;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Реализация с JdbcTemplate и DataSource.
 */
@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public AuthorRepositoryJdbc(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT * FROM author", (rs, i) ->
                new Author(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"))
        );
    }

    @Override
    public Author findById(Long id) {
        return jdbc.queryForObject("SELECT * FROM author WHERE id = ?",
                new Object[]{id}, new AuthorRowMapper());
    }

    @Override
    public void insert(Author author) {
        String sql = "INSERT INTO author (id, first_name, last_name) VALUES (?,?,?)";
        jdbc.update(sql, author.getId(), author.getFirstName(), author.getLastName());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM author WHERE id = ?", id);
    }

    @Override
    public void update(Long id, Author author) {
        jdbc.update("UPDATE author SET last_name = ?, first_name = ? WHERE id = ?",
                author.getLastName(), author.getFirstName(), author.getId());
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            return new Author(id, firstName, lastName);
        }
    }
}
