package com.epam.rd.library.clients;

import com.epam.rd.library.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient(name = "book-service",url = "http://localhost:8081/books")
public interface BookClient {
    @GetMapping
    List<BookDto> getAllBooks();
    @GetMapping("{book_id}")
    BookDto getBook(@PathVariable("book_id") int book_id);
    @PostMapping()
    BookDto addNewBook(@Valid @RequestBody BookDto bookDto);
    @DeleteMapping("{book_id}")
    ResponseEntity<Map<String,String>> deleteBook(@PathVariable("book_id") int book_id);
    @PutMapping("{book_id}")
    BookDto updateBook(@PathVariable("book_id") int book_id,@RequestBody BookDto bookDto);
}
