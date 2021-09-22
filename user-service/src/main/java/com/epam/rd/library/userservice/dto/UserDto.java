package com.epam.rd.library.userservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "Username should not be blank")
    private String username;
    @NotBlank(message = "Email should not be blank")
    private String email;

    public UserDto(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
}
