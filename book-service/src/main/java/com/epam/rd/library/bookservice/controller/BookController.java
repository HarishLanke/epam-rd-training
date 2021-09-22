package com.epam.rd.library.bookservice.controller;

import com.epam.rd.library.bookservice.dto.BookDto;
import com.epam.rd.library.bookservice.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;
    @GetMapping
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("{book_id}")
    public BookDto getBook(@PathVariable("book_id") int book_id){
        return bookService.getBook(book_id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BookDto addNewBook(@Valid @RequestBody BookDto bookDto){
        return bookService.addNewBook(bookDto);
    }
    @DeleteMapping("{book_id}")
    public ResponseEntity<Map<String,String>> deleteBook(@PathVariable("book_id") int book_id){
        return bookService.deleteBook(book_id);
    }
    @PutMapping("{book_id}")
    public BookDto updateBook(@PathVariable("book_id") int book_id,@RequestBody BookDto bookDto){
        return bookService.updateBook(book_id,bookDto);
    }

}
