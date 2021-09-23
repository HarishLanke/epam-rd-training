package com.epam.rd.library.clients;

import com.epam.rd.library.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient("book-service")
public interface BookClient {
    @GetMapping("books")
    List<BookDto> getAllBooks();
    @GetMapping("books/{book_id}")
    BookDto getBook(@PathVariable("book_id") int book_id);
    @PostMapping("books")
    BookDto addNewBook(@Valid @RequestBody BookDto bookDto);
    @DeleteMapping("books/{book_id}")
    ResponseEntity<Map<String,String>> deleteBook(@PathVariable("book_id") int book_id);
    @PutMapping("books/{book_id}")
    BookDto updateBook(@PathVariable("book_id") int book_id,@RequestBody BookDto bookDto);
}
