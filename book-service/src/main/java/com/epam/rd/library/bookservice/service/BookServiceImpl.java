package com.epam.rd.library.bookservice.service;

import com.epam.rd.library.bookservice.dto.BookDto;
import com.epam.rd.library.bookservice.exception.DataNotFoundException;
import com.epam.rd.library.bookservice.exception.DuplicateDataException;
import com.epam.rd.library.bookservice.model.Book;
import com.epam.rd.library.bookservice.repository.BookRepository;
import com.epam.rd.library.bookservice.service.interfaces.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBook(int book_id) {
        Optional<Book> book = bookRepository.findById(book_id);
        if(!book.isPresent()){
            throw new DataNotFoundException("Book does not exist with id : "+book_id);
        }
        return convertToDto(book.get());
    }

    @Override
    public BookDto addNewBook(BookDto bookDto) {
        checkForDuplicates(bookDto);
        Book savedBook = bookRepository.save(convertToEntity(bookDto));
        return convertToDto(savedBook);
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteBook(int book_id) {
        BookDto bookDto = getBook(book_id);
        bookRepository.delete(convertToEntity(bookDto));
        Map<String,String> response = new HashMap<>();
        response.put("success","Book deleted with id : "+book_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public BookDto updateBook(int book_id, BookDto bookDto) {
        BookDto existingBook = getBook(book_id);
        if(!existingBook.getName().equals(bookDto.getName())){
            checkForDuplicates(bookDto);
        }
        existingBook.setName(bookDto.getName());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPublisher(bookDto.getPublisher());
        bookRepository.save(convertToEntity(existingBook));
        return getBook(book_id);
    }

    private void checkForDuplicates(BookDto bookDto){
        if(bookRepository.existsByName(bookDto.getName())){
            throw new DuplicateDataException("Book already exists with name : "+bookDto.getName());
        }
    }
    private  BookDto convertToDto(Book book){
        return mapper.map(book,BookDto.class);
    }

    private  Book convertToEntity(BookDto bookDto){
        return mapper.map(bookDto,Book.class);
    }
}
