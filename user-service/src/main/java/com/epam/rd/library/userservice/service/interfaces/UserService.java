package com.epam.rd.library.userservice.service.interfaces;

import com.epam.rd.library.userservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUser(String username);

    UserDto addNewUser(UserDto userDto);

    ResponseEntity<Map<String, String>> deleteUser(String username);

    UserDto updateUser(String username, UserDto userDto);
}
