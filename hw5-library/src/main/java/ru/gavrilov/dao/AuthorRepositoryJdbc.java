package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public AuthorRepositoryJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
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
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM author WHERE id = :id", params, new AuthorRowMapper());
    }

    @Override
    public void insert(Author author) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("firstName", author.getFirstName());
        params.put("lastName", author.getLastName());
        String sql = "INSERT INTO author (id, first_name, last_name) VALUES (:id, :firstName, :lastName)";
        jdbc.update(sql, params);
    }

    @Override
    public void deleteById(Long id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("deleteId", id);
        jdbc.update("DELETE FROM author WHERE id = :deleteId", params);
    }

    @Override
    public void update(Long id, Author author) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("updId", author.getId());
        params.put("updFirstName", author.getFirstName());
        params.put("updLastName", author.getLastName());
        jdbc.update("UPDATE author SET last_name = :updLastName, first_name = :updFirstName WHERE id = :updId", params);
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
