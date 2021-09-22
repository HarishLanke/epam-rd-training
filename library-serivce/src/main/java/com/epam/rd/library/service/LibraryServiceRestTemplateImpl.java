package com.epam.rd.library.service;

import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.LibraryDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.model.Library;
import com.epam.rd.library.repository.LibraryRepository;
import com.epam.rd.library.service.interfaces.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LibraryServiceRestTemplateImpl implements LibraryService {
    private String BOOK_SERVICE_BASE_URI = "http://localhost:8081/books";
    private String USER_SERVICE_BASE_URI = "http://localhost:8082/users";
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper mapper;
    @Override
    public List<BookDto> getAllBooks() {
        return restTemplate.getForObject(BOOK_SERVICE_BASE_URI, List.class);
    }

    @Override
    public BookDto getBookById(int book_id) {
        return restTemplate.getForObject(BOOK_SERVICE_BASE_URI+ "/{book_id}",
                BookDto.class,
                book_id);
    }

    @Override
    public BookDto addNewBook(BookDto bookDto) {
        return restTemplate.postForObject(BOOK_SERVICE_BASE_URI, bookDto, BookDto.class);
    }

    @Override
    public ResponseEntity<Map<String,String>> deleteBookById(int book_id) {
        libraryRepository.deleteAllByBookId(book_id);
        restTemplate.delete(BOOK_SERVICE_BASE_URI+"/{book_id}",book_id);
        Map<String,String> response = new HashMap<>();
        response.put("success","Book deleted with id : "+book_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public BookDto updateBook(int book_id, BookDto bookDto) {
        HttpEntity<BookDto> requestEntity = new HttpEntity<BookDto>(bookDto,null);
        ResponseEntity<BookDto> responseEntity = restTemplate.exchange(
                BOOK_SERVICE_BASE_URI + "/{book_id}",
                HttpMethod.PUT,
                requestEntity,
                BookDto.class,
                book_id);
        return responseEntity.getBody();
    }



    @Override
    public List<UserDto> getAllUsers() {
        return restTemplate.getForObject(USER_SERVICE_BASE_URI, List.class);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserDto userDto = restTemplate.getForObject(USER_SERVICE_BASE_URI+ "/{username}", UserDto.class,username);
        List<Library> libraries = libraryRepository.findAllByUsername(username);
        libraries.forEach(library -> {
            BookDto book = getBookById(library.getBookId());
            userDto.getBooks().add(book.getName());
        });
        return userDto;

    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return restTemplate.postForObject(USER_SERVICE_BASE_URI, userDto, UserDto.class);
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteUserByUsername(String username) {
        libraryRepository.deleteAllByUsername(username);
        restTemplate.delete(USER_SERVICE_BASE_URI+"/"+username);
        Map<String,String> response = new HashMap<>();
        response.put("success","User deleted with username : "+username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public UserDto updateUser(String username, UserDto userDto) {
        HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(userDto,null);
        ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
                USER_SERVICE_BASE_URI + "/{username}",
                HttpMethod.PUT,
                requestEntity,
                UserDto.class,
                username);
        return responseEntity.getBody();
    }

    @Override
    public UserDto issueBookToUser(String username, int book_id) {
        libraryRepository.save(convertToEntity(new LibraryDto(username, book_id)));
        return getUserByUsername(username);
    }

    @Override
    public UserDto releaseBookForUser(String username, int book_id) {
        Library library = libraryRepository.findByUsernameAndBookId(username, book_id);
        libraryRepository.delete(library);
        return getUserByUsername(username);
    }

    private  Library convertToEntity(LibraryDto libraryDto){
        return mapper.map(libraryDto,Library.class);
    }

}
