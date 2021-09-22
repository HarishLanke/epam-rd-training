package com.epam.rd.library.service;

import com.epam.rd.library.clients.BookClient;
import com.epam.rd.library.clients.UserClient;
import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.LibraryDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.exception.DataNotFoundException;
import com.epam.rd.library.exception.DuplicateDataException;
import com.epam.rd.library.model.Library;
import com.epam.rd.library.repository.LibraryRepository;
import com.epam.rd.library.service.interfaces.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LibraryServiceFeignImpl implements LibraryService {
    @Autowired
    private BookClient bookClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<BookDto> getAllBooks() {
        return bookClient.getAllBooks();
    }

    @Override
    public BookDto getBookById(int book_id) {
        return bookClient.getBook(book_id);
    }

    @Override
    public BookDto addNewBook(BookDto bookDto) {
        return bookClient.addNewBook(bookDto);
    }

    @Override
    public ResponseEntity<Map<String,String>> deleteBookById(int book_id) {
        libraryRepository.deleteAllByBookId(book_id);
        return bookClient.deleteBook(book_id);
    }
    @Override
    public BookDto updateBook(int book_id, BookDto bookDto) {
        return bookClient.updateBook(book_id,bookDto);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = userClient.getAllUsers();
        allUsers.forEach(this::addAllAssignedBooks);
        return allUsers;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserDto userDto = userClient.getUser(username);
        addAllAssignedBooks(userDto);
        return userDto;
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return userClient.addNewUser(userDto);
    }



    @Override
    public ResponseEntity<Map<String, String>> deleteUserByUsername(String username) {
        libraryRepository.deleteAllByUsername(username);
        return userClient.deleteUser(username);
    }

    @Override
    public UserDto updateUser(String username, UserDto userDto) {
        return userClient.updateUser(username,userDto);
    }

    @Override
    public UserDto issueBookToUser(String username, int book_id) {
        if(libraryRepository.existsByUsernameAndBookId(username,book_id)){
            throw new DuplicateDataException("Book with id : "+book_id+" is already issued to username : "+username);
        }
        libraryRepository.save(convertToEntity(new LibraryDto(username, book_id)));
        return getUserByUsername(username);
    }

    @Override
    public UserDto releaseBookForUser(String username, int book_id) {
        if(!libraryRepository.existsByUsernameAndBookId(username,book_id)){
            throw new DataNotFoundException("Book not found with id : "+book_id+" for username : "+username);
        }
        Library library = libraryRepository.findByUsernameAndBookId(username, book_id);
        libraryRepository.delete(library);
        return getUserByUsername(username);
    }

    private void addAllAssignedBooks(UserDto userDto){
        List<Library> libraries = libraryRepository.findAllByUsername(userDto.getUsername());
        libraries.forEach(library -> {
            BookDto book = getBookById(library.getBookId());
            userDto.getBooks().add(book.getName());
        });
    }
    private Library convertToEntity(LibraryDto libraryDto){
        return mapper.map(libraryDto,Library.class);
    }

}
