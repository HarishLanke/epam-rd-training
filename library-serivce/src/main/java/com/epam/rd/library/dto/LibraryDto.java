package com.epam.rd.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LibraryDto {
    private int id;
    private String username;
    private int bookId;

    public LibraryDto(String username, int bookId) {
        this.username = username;
        this.bookId = bookId;
    }
}
