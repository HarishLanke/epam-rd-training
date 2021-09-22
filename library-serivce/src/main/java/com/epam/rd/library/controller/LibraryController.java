package com.epam.rd.library.controller;

import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.service.LibraryServiceFeignImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryServiceFeignImpl libraryService;
    @GetMapping("/books")
    public List<BookDto> getAllBooks(){
        return libraryService.getAllBooks();
    }
    @GetMapping("/books/{book_id}")
    public BookDto getBookById(@PathVariable("book_id") int book_id){
        return libraryService.getBookById(book_id);
    }
    @PostMapping(value ="/books",consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createNewBook(@Valid @RequestBody BookDto bookDto){
        return libraryService.addNewBook(bookDto);
    }
    @DeleteMapping("/books/{book_id}")
    public ResponseEntity<Map<String,String>> deleteBookById(@PathVariable("book_id") int book_id){
        return libraryService.deleteBookById(book_id);
    }
    @PutMapping(value = "/books/{book_id}",consumes= MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBookById(@PathVariable("book_id") int book_id,@Valid @RequestBody BookDto bookDto){
        return libraryService.updateBook(book_id,bookDto);
    }
    @GetMapping("/users")
    public List<UserDto> getAllUsers(){
        return libraryService.getAllUsers();
    }
    @GetMapping("/users/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username){
        return libraryService.getUserByUsername(username);
    }
    @PostMapping(value ="/users",consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createNewUser(@Valid @RequestBody UserDto userDto){
        return libraryService.addNewUser(userDto);
    }
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Map<String, String>> deleteBookByUser(@PathVariable("username") String username){
        return libraryService.deleteUserByUsername(username);
    }
    @PutMapping(value = "/users/{username}",consumes= MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUserByUsername(@PathVariable("username") String username,@Valid @RequestBody UserDto userDto){
        return libraryService.updateUser(username,userDto);
    }

    @PostMapping("/users/{username}/books/{book_id}")
    public UserDto issueBookToUser(@PathVariable("username") String username,
                                   @PathVariable("book_id") int book_id){
        return libraryService.issueBookToUser(username,book_id);
    }
    @DeleteMapping("/users/{username}/books/{book_id}")
    public UserDto releaseBookForUser(@PathVariable("username") String username,
                                   @PathVariable("book_id") int book_id){
        return libraryService.releaseBookForUser(username,book_id);
    }
}
