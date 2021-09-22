package com.epam.rd.library.userservice.repository;

import com.epam.rd.library.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
