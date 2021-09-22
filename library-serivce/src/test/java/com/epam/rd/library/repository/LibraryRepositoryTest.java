package com.epam.rd.library.repository;

import com.epam.rd.library.model.Library;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class LibraryRepositoryTest {
    @Autowired
    private LibraryRepository libraryRepository;
    @MockBean
    private RestTemplateBuilder restTemplateBuilder;
    @Test
    public void findByUsernameAndBookId(){
        Library library = new Library();
        library.setUsername("harish");
        library.setBookId(1);
        libraryRepository.save(library);
        Library fetchedLibrary = libraryRepository.findByUsernameAndBookId("harish", 1);
        Assertions.assertEquals(library.getUsername(),fetchedLibrary.getUsername());
        Assertions.assertEquals(library.getBookId(),fetchedLibrary.getBookId());
    }
}
