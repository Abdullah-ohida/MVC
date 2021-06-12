package com.example.blogger.data.repositories;

import com.example.blogger.data.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByUserName(String lastName);
    boolean existsByPassword(String password);
}
