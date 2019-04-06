package ru.gavrilov.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.gavrilov.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int i) throws SQLException {
        long id = rs.getLong("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        return new Author(id, firstName, lastName);
    }
}