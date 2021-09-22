package com.epam.rd.library.bookservice.repository;

import com.epam.rd.library.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book,Integer> {
    boolean existsByName(String name);
}
