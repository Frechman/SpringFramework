package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Реализация с NamedParameterJdbcOperations.
 */
@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookRepositoryJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM book",
                (rs, i) -> new Book(rs.getString("isbn"),
                        rs.getString("title"), rs.getLong("publish_year"),
                        rs.getLong("genre_id"), rs.getLong("author_id")));
    }

    @Override
    public Book findByIsbn(String isbn) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("isbn", isbn);
        return jdbc.queryForObject("SELECT * FROM book WHERE isbn = :isbn", params, new BookRowMapper());
    }

    @Override
    public void insert(Book book) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("publishYear", book.getPublishYear());
        params.put("genreId", book.getGenreId());
        params.put("authorId", book.getAuthorId());
        String sql = "INSERT INTO book (isbn, title, publish_year, genre_id, author_id) " +
                "VALUES (:isbn, :title,:publishYear, :genreId, :authorId)";
        jdbc.update(sql, params);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("isbn", isbn);
        jdbc.update("DELETE FROM book WHERE isbn = :isbn", params);
    }

    @Override
    public List<Book> findAllByGenre(String genreName) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("genreName", genreName);
        String sql = "SELECT b.* FROM book b JOIN genre g ON g.id = b.genre_id WHERE g.name ILIKE :genreName";
        return jdbc.query(sql, params, new BookRowMapper());
    }

    @Override
    public List<Book> findAllByAuthor(String authorLastName) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("authorLastName", authorLastName);
        String sql = "SELECT b.* FROM book b JOIN author a ON a.id = b.author_id WHERE a.last_name ILIKE :authorLastName";
        return jdbc.query(sql, params, new BookRowMapper());
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT count(*) FROM book", Integer.class);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getString("isbn"),
                    rs.getString("title"), rs.getLong("publish_year"),
                    rs.getLong("genre_id"), rs.getLong("author_id"));
        }
    }
}
