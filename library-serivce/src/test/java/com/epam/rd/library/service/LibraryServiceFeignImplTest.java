package com.epam.rd.library.service;

import com.epam.rd.library.clients.BookClient;
import com.epam.rd.library.clients.UserClient;
import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.model.Library;
import com.epam.rd.library.repository.LibraryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceFeignImplTest {
    @InjectMocks
    LibraryServiceFeignImpl libraryService;
    @Mock
    private BookClient bookClient;
    @Mock
    private UserClient userClient;
    @Mock
    LibraryRepository libraryRepository;
    @Spy
    ModelMapper mapper;
    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() {
        List<BookDto> books = List.of(
                new BookDto("python","",""),
                new BookDto("java","",""),
                new BookDto("microservice","","")
        );
        when(bookClient.getAllBooks()).thenReturn(books);
        List<BookDto> allBooks = libraryService.getAllBooks();
        Assertions.assertEquals(books.get(0).getName(),allBooks.get(0).getName());
        Assertions.assertEquals(books.get(1).getName(),allBooks.get(1).getName());
        Assertions.assertEquals(books.get(2).getName(),allBooks.get(2).getName());
    }
    @Test
    @DisplayName("Should return book by id")
    void shouldReturnBookById() {
        BookDto bookDto = new BookDto("python","","");
        when(bookClient.getBook(anyInt())).thenReturn(bookDto);
        BookDto book = libraryService.getBookById(1);
        Assertions.assertEquals(bookDto.getName() , book.getName());
    }

    @Test
    @DisplayName("Should return new added book")
    void shouldReturnNewAddedBook() {
        BookDto bookDto = new BookDto("python","","");
        when(bookClient.addNewBook(any())).thenReturn(bookDto);
        BookDto addedBook = libraryService.addNewBook(bookDto);
        Assertions.assertEquals(bookDto.getName() , addedBook.getName());
    }

    @Test
    @DisplayName("Should delete book by id")
    void shouldDeleteBookById() {
        doNothing().when(libraryRepository).deleteAllByBookId(1);
        Map<String,String> response = new HashMap<>();
        response.put("success","Book Deleted");
        ResponseEntity<Map<String,String>> responseEntity = new ResponseEntity<>(response,HttpStatus.OK);
        when(bookClient.deleteBook(anyInt())).thenReturn(responseEntity);
        libraryService.deleteBookById(1);
    }

    @Test
    @DisplayName("Should return updated book by id")
    void shouldReturnUpdatedBookById() {
        BookDto bookDto = new BookDto("python","","");
        when(bookClient.updateBook(anyInt(),any())).thenReturn(bookDto);
        BookDto updatedBook = libraryService.updateBook(1,bookDto);
        Assertions.assertEquals(bookDto.getName() , updatedBook.getName());
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        List<UserDto> users = List.of(
                new UserDto("harish99","",""),
                new UserDto("suresh98","",""),
                new UserDto("harish123","","")
        );
        when(userClient.getAllUsers()).thenReturn(users);
        List<UserDto> allUsers = libraryService.getAllUsers();
        Assertions.assertEquals(users.get(0).getName(),allUsers.get(0).getName());
        Assertions.assertEquals(users.get(1).getName(),allUsers.get(1).getName());
        Assertions.assertEquals(users.get(2).getName(),allUsers.get(2).getName());
    }

    @Test
    @DisplayName("Should return user by username")
    void shouldReturnUserByUsername() {
        UserDto userDto = new UserDto("harish","","");
        when(userClient.getUser(
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.getUserByUsername("harish");
        Assertions.assertEquals(userDto.getName() , user.getName());
    }

    @Test
    @DisplayName("Should return new added user")
    void shouldReturnNewAddedUser() {
        UserDto userDto = new UserDto("python","","");
        when(userClient.addNewUser(any())).thenReturn(userDto);
        UserDto addedUser = libraryService.addNewUser(userDto);
        Assertions.assertEquals(userDto.getName() , addedUser.getName());
    }

    @Test
    @DisplayName("Should delete user by username")
    void shouldDeleteUserByUsername() {
        Map<String,String> response = new HashMap<>();
        response.put("success","User Deleted");
        ResponseEntity<Map<String,String>> responseEntity = new ResponseEntity<>(response,HttpStatus.OK);
        when(userClient.deleteUser(anyString())).thenReturn(responseEntity);
        libraryService.deleteUserByUsername("1");
    }

    @Test
    @DisplayName("Should return updated user by username")
    void shouldReturnUpdatedUserByUsername() {
        UserDto userDto = new UserDto("harish","","");
        when(userClient.updateUser(
                anyString(),any()
        )).thenReturn(userDto);
        UserDto updatedUser = libraryService.updateUser("harish",userDto);
        Assertions.assertEquals(userDto.getName() , updatedUser.getName());
    }

    @Test
    @DisplayName("Should return user with new issued book")
    void shouldReturnUserWithNewIssuedBook() {
        Library library = new Library("harish",1);
        when(libraryRepository.save(any())).thenReturn(library);
        UserDto userDto = new UserDto("harish","","");
        when(userClient.getUser(
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.issueBookToUser("harish",1);
        Assertions.assertEquals(userDto.getName() , user.getName());
    }

    @Test
    @DisplayName("Should return user with releasing book by id")
    void shouldReturnUserWithReleasingBookById() {
        Library library = new Library("harish",1);
        when(libraryRepository.existsByUsernameAndBookId(anyString(),anyInt())).thenReturn(true);
        when(libraryRepository.findByUsernameAndBookId(anyString(),anyInt())).thenReturn(library);
        doNothing().when(libraryRepository).delete(any());
        UserDto userDto = new UserDto("harish","","");
        when(userClient.getUser(
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.releaseBookForUser("harish",1);
        Assertions.assertEquals(userDto.getName() , user.getName());
    }
}