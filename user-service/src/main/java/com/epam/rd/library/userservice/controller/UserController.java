package com.epam.rd.library.userservice.controller;

import com.epam.rd.library.userservice.dto.UserDto;
import com.epam.rd.library.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("{username}")
    public UserDto getUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDto addNewUser(@Valid @RequestBody UserDto userDto){
        return userService.addNewUser(userDto);
    }
    @DeleteMapping("{username}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("username") String username){
        return userService.deleteUser(username);
    }
    @PutMapping("{username}")
    public UserDto updateUser(@PathVariable("username") String username,@RequestBody UserDto userDto){
        return userService.updateUser(username,userDto);
    }

}
