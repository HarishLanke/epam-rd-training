package com.epam.rd.library.controller;

import com.epam.rd.library.dto.BookDto;
import com.epam.rd.library.dto.UserDto;
import com.epam.rd.library.repository.LibraryRepository;
import com.epam.rd.library.service.LibraryServiceFeignImpl;
import com.epam.rd.library.service.LibraryServiceRestTemplateImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LibraryServiceRestTemplateImpl libraryService;
    @MockBean
    private LibraryServiceFeignImpl libraryServiceFeign;
    @MockBean
    private LibraryRepository libraryRepository;
    @MockBean
    private RestTemplateBuilder restTemplateBuilder;
    @Test
    @DisplayName("Should test uri mapping to get all books")
    void shouldTestURIMappingToGetAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to get book by id")
    void shouldTestURIMappingToGetBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/books/21")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to create new book")
    void shouldTestURIMappingToCreateNewBook() throws Exception {
        BookDto bookDto = new BookDto("java","publisher","author");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/books")
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Should test uri mapping to create new book1")
    void shouldTestURIMappingToCreateNewBook1() throws Exception {
        BookDto bookDto = new BookDto("","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/books")
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should test uri mapping to delete book by id")
    void shouldTestURIMappingToDeleteBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/library/books/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to update book by id")
    void shouldTestURIMappingToUpdateBookById() throws Exception {
        BookDto bookDto = new BookDto("java","publisher","author");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/library/books/3")
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to get all users")
    void shouldTestURIMappingToGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to get user by username")
    void shouldTestURIMappingToGetUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/users/harish1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to create new user")
    void shouldTestURIMappingToCreateNewUser() throws Exception {
        UserDto userDto = new UserDto("harish","harish9","harish@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/users")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Should test uri mapping to create new user1")
    void shouldTestURIMappingToCreateNewUser1() throws Exception {
        UserDto userDto = new UserDto("","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/users")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should test uri mapping to delete user by username")
    void shouldTestURIMappingToDeleteUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/library/users/harish1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to update user by username")
    void shouldTestURIMappingToUpdateUserByUsername() throws Exception {
        UserDto userDto = new UserDto("harish","harish9","harish@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/library/users/harish")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to issue book")
    void shouldTestURIMappingToIssueNewBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/users/harish/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to release book")
    void shouldTestURIMappingToReleaseBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/library/users/harish/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}