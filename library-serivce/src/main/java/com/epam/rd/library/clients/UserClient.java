package com.epam.rd.library.clients;

import com.epam.rd.library.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service",url = "http://localhost:8082/users")
public interface UserClient {
    @GetMapping
    List<UserDto> getAllUsers();
    @GetMapping("{username}")
    UserDto getUser(@PathVariable("username") String username);
    @PostMapping()
    UserDto addNewUser(@Valid @RequestBody UserDto userDto);
    @DeleteMapping("{username}")
    ResponseEntity<Map<String,String>> deleteUser(@PathVariable("username") String username);
    @PutMapping("{username}")
    UserDto updateUser(@PathVariable("username") String username,@RequestBody UserDto userDto);
}
