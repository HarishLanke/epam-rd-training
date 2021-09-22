package com.epam.rd.library.bookservice.controller;

import com.epam.rd.library.bookservice.model.Book;
import com.epam.rd.library.bookservice.repository.BookRepository;
import com.epam.rd.library.bookservice.service.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Test
    @DisplayName("Should test uri mapping to get all books")
    void shouldTestURIMappingToGetAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to get book by id")
    void shouldTestURIMappingToGetBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to create new book")
    void shouldTestURIMappingToCreateNewBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/books")
                        .content("{\n" +
                                "    \"name\":\"3 States\",\n" +
                                "    \"author\":\"Chetan Bhagat\",\n" +
                                "    \"publisher\":\"Amazon Kindle\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should test uri mapping to delete book by id")
    void shouldTestURIMappingToDeleteBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/books/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to update book by id")
    void shouldTestURIMappingToUpdateBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/books/3")
                        .content("{\n" +
                                "    \"id\": 4,\n" +
                                "    \"name\": \"2 states\",\n" +
                                "    \"publisher\": \"Amazon Kindle\",\n" +
                                "    \"author\": \"Chetan Bhagat\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}