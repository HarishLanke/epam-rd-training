package com.epam.rd.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
    private int id;
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "Publisher should not be blank")
    private String publisher;
    @NotBlank(message = "Author should not be blank")
    private String author;

    public BookDto(String name, String publisher, String author) {

        this.name = name;
        this.publisher = publisher;
        this.author = author;
    }
}
