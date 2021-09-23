package com.epam.rd.library.clients;

import com.epam.rd.library.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("users")
    List<UserDto> getAllUsers();
    @GetMapping("users/{username}")
    UserDto getUser(@PathVariable("username") String username);
    @PostMapping("users")
    UserDto addNewUser(@Valid @RequestBody UserDto userDto);
    @DeleteMapping("users/{username}")
    ResponseEntity<Map<String,String>> deleteUser(@PathVariable("username") String username);
    @PutMapping("users/{username}")
    UserDto updateUser(@PathVariable("username") String username,@RequestBody UserDto userDto);
}
