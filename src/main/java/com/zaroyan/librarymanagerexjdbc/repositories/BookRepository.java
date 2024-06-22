package com.zaroyan.librarymanagerexjdbc.repositories;

import com.zaroyan.librarymanagerexjdbc.models.Book;

/**
 * @author Zaroyan
 */
public interface BookRepository {
    Book findById(Long id);
    Iterable<Book> findAll();
    Book save(Book book);
    void deleteById(Long id);
}
