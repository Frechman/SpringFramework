package ru.gavrilov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.gavrilov.mapper.BookMapper;
import ru.gavrilov.model.Book;

import java.util.HashMap;
import java.util.List;

/**
 * Реализация с NamedParameterJdbcOperations.
 */
@Repository
public class BookRepositoryJdbc implements BookRepository {

    private static final String SELECT =
            "SELECT * FROM book b INNER JOIN author a ON a.id = b.author_id INNER JOIN genre g ON g.id = b.genre_id";
    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper bookMapper;

    @Autowired
    public BookRepositoryJdbc(NamedParameterJdbcOperations jdbc, BookMapper bookMapper) {
        this.jdbc = jdbc;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(SELECT, bookMapper);
    }

    /**
     * С использованием SqlParameterSource.
     */
    @Override
    public Book findByIsbn(String isbn) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("isbn", isbn);
        return jdbc.queryForObject(SELECT + " WHERE isbn = :isbn", params, bookMapper);
    }

    /**
     * С использованием SqlParameterSource.
     */
    @Override
    public void insert(Book book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("isbn", book.getIsbn())
                .addValue("title", book.getTitle())
                .addValue("publishYear", book.getPublishYear())
                .addValue("genreId", book.getGenre().getId())
                .addValue("authorId", book.getAuthor().getId());
        String sql = "INSERT INTO book (isbn, title, publish_year, genre_id, author_id) " +
                "VALUES (:isbn, :title,:publishYear, :genreId, :authorId)";
        jdbc.update(sql, params);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("isbn", isbn);
        jdbc.update("DELETE FROM book WHERE isbn = :isbn", params);
    }

    @Override
    public List<Book> findAllByGenre(String genreName) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("genreName", genreName);
        String sql = SELECT + " WHERE g.name ILIKE :genreName";
        return jdbc.query(sql, params, bookMapper);
    }

    @Override
    public List<Book> findAllByAuthor(String authorLastName) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("authorLastName", authorLastName);
        String sql = SELECT + " WHERE a.last_name ILIKE :authorLastName";
        return jdbc.query(sql, params, bookMapper);
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT count(*) FROM book", Integer.class);
    }
}
