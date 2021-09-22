package com.epam.rd.library.bookservice.service;

import com.epam.rd.library.bookservice.dto.BookDto;
import com.epam.rd.library.bookservice.exception.DataNotFoundException;
import com.epam.rd.library.bookservice.model.Book;
import com.epam.rd.library.bookservice.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Spy
    private ModelMapper mapper;
    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() {
        List<Book> books = List.of(
                new Book(1,"microservice","",""),
                new Book(1,"java","",""),
                new Book(1,"python","","")
                );
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> allBooks = bookService.getAllBooks();
        Assertions.assertEquals(books.get(0).getName(),allBooks.get(0).getName());
        Assertions.assertEquals(books.get(1).getName(),allBooks.get(1).getName());
        Assertions.assertEquals(books.get(2).getName(),allBooks.get(2).getName());
    }

    @Test
    @DisplayName("Should return book by id")
    void shouldReturnBookById() {
        Book book = new Book(1,"microservice","","");
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        BookDto bookDto = bookService.getBook(anyInt());
        Assertions.assertEquals(book.getName(),bookDto.getName());
    }
    @Test
    @DisplayName("Should return book by id1")
    void shouldReturnBookById1() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class,()->{
            bookService.getBook(anyInt());
        });
    }

    @Test
    @DisplayName("Should return new added book")
    void shouldReturnNewAddedBook() {
        Book book = new Book(1,"microservice","","");
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.addNewBook(new BookDto());
        Assertions.assertEquals(book.getName(),bookDto.getName());
    }

    @Test
    @DisplayName("Should delete book by id")
    void shouldDeleteBookById() {
        Book book = new Book(1,"microservice","","");
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        final BookDto[] bookDto = new BookDto[1];
        Assertions.assertDoesNotThrow(()->{
            bookService.deleteBook(1);
        });
    }

    @Test
    @DisplayName("Should return updated book by id")
    void shouldReturnUpdatedBookById() {
        Book book = new Book(1,"microservice","","");
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        BookDto bookDto = bookService.updateBook(1, new BookDto("java", "", ""));
        Assertions.assertEquals(book.getName(),bookDto.getName());
    }
}