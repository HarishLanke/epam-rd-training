package com.epam.rd.library.userservice.controller;

import com.epam.rd.library.userservice.repository.UserRepository;
import com.epam.rd.library.userservice.service.UserServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Test
    @DisplayName("Should test uri mapping to get all users")
    void shouldTestURIMappingToGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to get user by username")
    void shouldTestURIMappingToGetUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/harish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to create new user")
    void shouldTestURIMappingToCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content("{\n" +
                                "    \"name\":\"Harish Lanke\",\n" +
                                "    \"username\":\"harish2\",\n" +
                                "    \"email\":\"harish@gmail.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should test uri mapping to delete user by username")
    void shouldTestURIMappingToDeleteUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/harish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should test uri mapping to update user by username")
    void shouldTestURIMappingToUpdateUserByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/harish")
                        .content("{\n" +
                                "    \"name\":\"Harish Lanke\",\n" +
                                "    \"username\":\"harish2\",\n" +
                                "    \"email\":\"harish@gmail.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}