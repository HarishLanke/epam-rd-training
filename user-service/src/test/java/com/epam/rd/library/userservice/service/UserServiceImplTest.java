package com.epam.rd.library.userservice.service;

import com.epam.rd.library.userservice.dto.UserDto;
import com.epam.rd.library.userservice.exception.DataNotFoundException;
import com.epam.rd.library.userservice.exception.DuplicateDataException;
import com.epam.rd.library.userservice.model.User;
import com.epam.rd.library.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper mapper;
    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        List<User> users = List.of(
                new User("harish","",""),
                new User("vishal","",""),
                new User("rohan","","")
        );
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> allUsers = userService.getAllUsers();
        Assertions.assertEquals(users.get(0).getName(),allUsers.get(0).getName());
        Assertions.assertEquals(users.get(1).getName(),allUsers.get(1).getName());
        Assertions.assertEquals(users.get(2).getName(),allUsers.get(2).getName());
    }

    @Test
    @DisplayName("Should return user by username")
    void shouldReturnUserByUsername() {
        User user = new User("harish","","");
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        UserDto userDto = userService.getUser(anyString());
        Assertions.assertEquals(user.getName(),userDto.getName());
    }
    @Test
    @DisplayName("Should return user by username1")
    void shouldReturnUserByUsername1() {
        User user = null;
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        Assertions.assertThrows(DataNotFoundException.class,()->{
            userService.getUser(anyString());
        });
    }

    @Test
    @DisplayName("Should return new added user")
    void shouldReturnNewAddedUser() {
        User user = new User("harish","","");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto userDto = userService.addNewUser(new UserDto());
        Assertions.assertEquals(user.getName(),userDto.getName());
    }
    @Test
    @DisplayName("Should return new added user1")
    void shouldReturnNewAddedUser1() {
        User user = new User("harish","","");
        when(userRepository.existsByUsername(any())).thenReturn(true);
        Assertions.assertThrows(DuplicateDataException.class,()->{
            userService.addNewUser(new UserDto());
        });
    }

    @Test
    @DisplayName("Should delete user by username")
    void shouldDeleteUserByUsername() {
        User user = new User("harish","","");
        when(userRepository.findByUsername(anyString())).thenReturn((user));
        final UserDto[] userDto = new UserDto[1];
        Assertions.assertDoesNotThrow(()->{
            userService.deleteUser("");
        });
    }

    @Test
    @DisplayName("Should return updated user by username")
    void shouldReturnUpdatedUserByUsername() {
        User user = new User("harish","","");
        when(userRepository.findByUsername(anyString())).thenReturn((user));
        UserDto userDto = userService.updateUser("harish", new UserDto("java", "", ""));
        Assertions.assertEquals(user.getName(),userDto.getName());
    }
}