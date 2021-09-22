package com.epam.rd.library.userservice.service;

import com.epam.rd.library.userservice.dto.UserDto;
import com.epam.rd.library.userservice.exception.DataNotFoundException;
import com.epam.rd.library.userservice.exception.DuplicateDataException;
import com.epam.rd.library.userservice.model.User;
import com.epam.rd.library.userservice.repository.UserRepository;
import com.epam.rd.library.userservice.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new DataNotFoundException("User does not exist with username : "+username);
        }
        return convertToDto(user);
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        checkForDuplicates(userDto);
        User savedUser = userRepository.save(convertToEntity(userDto));
        return convertToDto(savedUser);
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteUser(String username) {
        UserDto userDto = getUser(username);
        userRepository.delete(convertToEntity(userDto));
        Map<String,String> response = new HashMap<>();
        response.put("success","User deleted with username : "+username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public UserDto updateUser(String username, UserDto userDto) {
        UserDto existingUser = getUser(username);
        if(!existingUser.getUsername().equals(userDto.getUsername())){
            checkForDuplicates(userDto);
        }
        existingUser.setName(userDto.getName());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        userRepository.save(convertToEntity(existingUser));
        return getUser(username);
    }

    private void checkForDuplicates(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())){
            throw new DuplicateDataException("User already exists with username : "+ userDto.getUsername());
        }
    }

    private  UserDto convertToDto(User book){
        return mapper.map(book,UserDto.class);
    }

    private  User convertToEntity(UserDto bookDto){
        return mapper.map(bookDto,User.class);
    }
}
