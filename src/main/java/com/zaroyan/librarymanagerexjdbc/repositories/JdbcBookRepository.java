package com.zaroyan.librarymanagerexjdbc.repositories;

import com.zaroyan.librarymanagerexjdbc.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Zaroyan
 */
@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, title, author, publication_year FROM book WHERE id = ?",
                new Object[]{id},
                (RowMapper<Book>) this::mapRowToBook);
    }

    @Override
    public Iterable<Book> findAll() {
        return jdbcTemplate.query(
                "SELECT id, title, author, publication_year FROM book",
                this::mapRowToBook);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO book(title, author, publication_year) VALUES (?, ?, ?)",
                    book.getTitle(), book.getAuthor(), book.getPublicationYear());
        } else {
            jdbcTemplate.update(
                    "UPDATE book SET title = ?, author = ?, publication_year = ? WHERE id = ?",
                    book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
        }
        return book;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getInt("publication_year"));
        return book;
    }
}
