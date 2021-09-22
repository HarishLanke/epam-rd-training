package com.epam.rd.library.bookservice.service.interfaces;

import com.epam.rd.library.bookservice.dto.BookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBook(int book_id);

    BookDto addNewBook(BookDto bookDto);

    ResponseEntity<Map<String, String>> deleteBook(int book_id);

    BookDto updateBook(int book_id, BookDto bookDto);
}
