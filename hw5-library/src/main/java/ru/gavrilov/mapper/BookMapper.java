package ru.gavrilov.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.gavrilov.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {

    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;

    @Autowired
    public BookMapper(GenreMapper genreMapper, AuthorMapper authorMapper) {
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getString("isbn"),
                rs.getString("title"), rs.getLong("publish_year"),
                genreMapper.mapRow(rs, rowNum), authorMapper.mapRow(rs, rowNum));
    }
}