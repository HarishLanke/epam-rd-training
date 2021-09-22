package com.epam.rd.library.service.interfaces;

import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LibraryService {
    List<BookDto> getAllBooks();

    BookDto getBookById(int book_id);

    BookDto addNewBook(BookDto bookDto);

    ResponseEntity<Map<String,String>> deleteBookById(int book_id);

    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);

    UserDto addNewUser(UserDto userDto);

    ResponseEntity<Map<String, String>> deleteUserByUsername(String username);

    BookDto updateBook(int book_id, BookDto bookDto);

    UserDto updateUser(String username, UserDto userDto1);

    UserDto issueBookToUser(String username, int book_id);

    UserDto releaseBookForUser(String username, int book_id);
}
