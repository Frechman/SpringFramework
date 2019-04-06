package ru.gavrilov.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.gavrilov.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int i) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return new Genre(id, name, description);
    }
}