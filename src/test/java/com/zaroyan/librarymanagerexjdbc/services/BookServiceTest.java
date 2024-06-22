package com.zaroyan.librarymanagerexjdbc.services;

import com.zaroyan.librarymanagerexjdbc.models.Book;
import com.zaroyan.librarymanagerexjdbc.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Zaroyan
 */
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private static final Book TEST_BOOK_1 = new Book(1L, "Book 1", "Author 1", 1833);
    private static final Book TEST_BOOK_2 = new Book(2L, "Book 2", "Author 2", 2000);

    @BeforeEach
    public void setup() {
        lenient().when(bookRepository.findById(TEST_BOOK_1.getId())).thenReturn((TEST_BOOK_1));
        lenient().when(bookRepository.findById(TEST_BOOK_2.getId())).thenReturn((TEST_BOOK_2));

        lenient().when(bookRepository.findAll()).thenReturn(Arrays.asList(TEST_BOOK_1, TEST_BOOK_2));

        // Mocking save
        lenient().when(bookRepository.save(any(Book.class)))
                .thenAnswer(invocation -> {
                    Book book = invocation.getArgument(0);
                    if (book.getId() == null) {
                    }
                    return book;
                });

        lenient().doNothing().when(bookRepository).deleteById(anyLong());
    }

    @Test
    public void testFindById() {
        Book book = bookService.findById(TEST_BOOK_1.getId());
        assertEquals(TEST_BOOK_1.getId(), book.getId());
        assertEquals(TEST_BOOK_1.getTitle(), book.getTitle());
        assertEquals(TEST_BOOK_1.getAuthor(), book.getAuthor());
        assertEquals(TEST_BOOK_1.getPublicationYear(), book.getPublicationYear());
    }

    @Test
    public void testFindAll() {
        List<Book> books = (List<Book>) bookService.findAll();
        assertEquals(2, books.size());
        assertEquals(TEST_BOOK_1.getId(), books.get(0).getId());
        assertEquals(TEST_BOOK_2.getId(), books.get(1).getId());
    }

    @Test
    public void testSave() {
        Book newBook = new Book(null, "New Book", "New Author", 2024);
        Book savedBook = bookService.save(newBook);
        assertEquals(newBook.getTitle(), savedBook.getTitle());
        assertEquals(newBook.getAuthor(), savedBook.getAuthor());
        assertEquals(newBook.getPublicationYear(), savedBook.getPublicationYear());
    }

    @Test
    public void testDeleteById() {
        bookService.deleteById(TEST_BOOK_1.getId());
        verify(bookRepository, times(1)).deleteById(TEST_BOOK_1.getId());
    }
}
