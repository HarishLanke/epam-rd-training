package com.epam.rd.library.service;

import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.model.Library;
import com.epam.rd.library.repository.LibraryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceRestTemplateImplTest {
    @InjectMocks
    LibraryServiceRestTemplateImpl libraryService;
    @Mock
    RestTemplate restTemplate;
    @Mock
    LibraryRepository libraryRepository;
    @Spy
    ModelMapper mapper;
    private String BOOK_SERVICE_BASE_URI = "http://localhost:8081/books";
    private String USER_SERVICE_BASE_URI = "http://localhost:8082/users";

    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() {
        List<BookDto> books = List.of(
                new BookDto("python","",""),
                new BookDto("java","",""),
                new BookDto("microservice","","")
        );
        when(restTemplate.getForObject(anyString(),any())).thenReturn(books);
        List<BookDto> allBooks = libraryService.getAllBooks();
        Assertions.assertEquals(books.get(0).getName(),allBooks.get(0).getName());
        Assertions.assertEquals(books.get(1).getName(),allBooks.get(1).getName());
        Assertions.assertEquals(books.get(2).getName(),allBooks.get(2).getName());
    }

    @Test
    @DisplayName("Should return book by id")
    void shouldReturnBookById() {
        BookDto bookDto = new BookDto("python","","");
        when(restTemplate.getForObject(anyString(),any(),anyInt())).thenReturn(bookDto);
        BookDto book = libraryService.getBookById(1);
        Assertions.assertEquals(bookDto.getName() , book.getName());
    }

    @Test
    @DisplayName("Should return new added book")
    void shouldReturnNewAddedBook() {
        BookDto bookDto = new BookDto("python","","");
        when(restTemplate.postForObject(anyString(),any(),any())).thenReturn(bookDto);
        BookDto addedBook = libraryService.addNewBook(bookDto);
        Assertions.assertEquals(bookDto.getName() , addedBook.getName());
    }

    @Test
    @DisplayName("Should delete book by id")
    void shouldDeleteBookById() {
        doNothing().when(libraryRepository).deleteAllByBookId(1);

        ResponseEntity<Map<String, String>> responseEntity = libraryService.deleteBookById(1);
        Assertions.assertTrue(responseEntity.getBody().containsKey("success"));
    }

    @Test
    @DisplayName("Should return updated book by id")
    void shouldReturnUpdatedBookById() {
        BookDto bookDto = new BookDto("python","","");
        when(restTemplate.exchange(
                ArgumentMatchers.eq(BOOK_SERVICE_BASE_URI+"/{book_id}"),
                ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(HttpEntity.class),
                ArgumentMatchers.eq(BookDto.class),
                ArgumentMatchers.eq(1)
        )).thenReturn(new ResponseEntity<>(bookDto,HttpStatus.OK));
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
        when(restTemplate.getForObject(anyString(),any())).thenReturn(users);
        List<UserDto> allUsers = libraryService.getAllUsers();
        Assertions.assertEquals(users.get(0).getName(),allUsers.get(0).getName());
        Assertions.assertEquals(users.get(1).getName(),allUsers.get(1).getName());
        Assertions.assertEquals(users.get(2).getName(),allUsers.get(2).getName());
    }

    @Test
    @DisplayName("Should return user by username")
    void shouldReturnUserByUsername() {
        UserDto userDto = new UserDto("harish","","");
        when(restTemplate.getForObject(
                anyString(),
                any(),
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.getUserByUsername("harish");
        Assertions.assertEquals(userDto.getName() , user.getName());
    }

    @Test
    @DisplayName("Should return new added user")
    void shouldReturnNewAddedUser() {
        UserDto userDto = new UserDto("python","","");
        when(restTemplate.postForObject(anyString(),any(),any())).thenReturn(userDto);
        UserDto addedUser = libraryService.addNewUser(userDto);
        Assertions.assertEquals(userDto.getName() , addedUser.getName());
    }

    @Test
    @DisplayName("Should delete user by username")
    void shouldDeleteUserByUsername() {
        doNothing().when(libraryRepository).deleteAllByUsername(anyString());
        doNothing().when(restTemplate).delete(anyString());
        ResponseEntity<Map<String, String>> responseEntity = libraryService.deleteUserByUsername("");
        libraryService.deleteUserByUsername("harish");
        Assertions.assertTrue(responseEntity.getBody().containsKey("success"));
    }

    @Test
    @DisplayName("Should return updated user by username")
    void shouldReturnUpdatedUserByUsername() {
        UserDto userDto = new UserDto("harish","","");
        when(restTemplate.exchange(
                ArgumentMatchers.eq(USER_SERVICE_BASE_URI+"/{username}"),
                ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(HttpEntity.class),
                ArgumentMatchers.eq(UserDto.class),
                ArgumentMatchers.eq("harish")
        )).thenReturn(new ResponseEntity<UserDto>(userDto,HttpStatus.OK));
        UserDto updatedUser = libraryService.updateUser("harish",userDto);
        Assertions.assertEquals(userDto.getName() , updatedUser.getName());
    }

    @Test
    @DisplayName("Should return user with new issued book")
    void shouldReturnUserWithNewIssuedBook() {
        Library library = new Library("harish",1);
        when(libraryRepository.save(any())).thenReturn(library);
        UserDto userDto = new UserDto("harish","","");
        when(restTemplate.getForObject(
                anyString(),
                any(),
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.issueBookToUser("harish",1);
        Assertions.assertEquals(userDto.getName() , user.getName());
    }

    @Test
    @DisplayName("Should return user with releasing book by id")
    void shouldReturnUserWithReleasingBookById() {
        Library library = new Library("harish",1);
        when(libraryRepository.findByUsernameAndBookId(anyString(),anyInt())).thenReturn(library);
        doNothing().when(libraryRepository).delete(any());
        UserDto userDto = new UserDto("harish","","");
        when(restTemplate.getForObject(
                anyString(),
                any(),
                anyString())).thenReturn(userDto);
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(new ArrayList<>());
        UserDto user = libraryService.releaseBookForUser("harish",1);
        Assertions.assertEquals(userDto.getName() , user.getName());
    }
}