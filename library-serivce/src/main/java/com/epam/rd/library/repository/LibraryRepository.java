package com.epam.rd.library.repository;

import com.epam.rd.library.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LibraryRepository extends JpaRepository<Library,Integer> {
    List<Library> findAllByUsername(String username);

    Library findByUsernameAndBookId(String username, int book_id);

    void deleteAllByBookId(int book_id);

    void deleteAllByUsername(String username);


    boolean existsByUsernameAndBookId(String username, int book_id);
}
