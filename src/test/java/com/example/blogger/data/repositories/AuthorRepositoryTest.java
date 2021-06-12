package com.example.blogger.data.repositories;

import com.example.blogger.data.model.Author;
import com.example.blogger.data.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Sql(scripts = "classpath:db/insert.sql")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setUserName("Abdul");
        author.setEmail("adex@gmail.com");
        author.setPassword("password");
        author.setProfession("Software Engineer");
        author.setProfileImage("/home/whalewalker/Whalewalker/Personal/blogger/src/main/resources/static/images/author.jpg");
    }

    @Test
    void saveAuthorToDatabaseTest(){
        log.info("Create an author --> {}", author);
        authorRepository.save(author);

        Author savedAuthor = authorRepository.findAuthorByUserName(author.getUserName());
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getProfileImage()).isEqualTo(author.getProfileImage());
    }

    @Test
    void throwExceptionWhenSavingAuthorWithExistingUserName(){
        log.info("Create an author --> {}", author);
        authorRepository.save(author);

        Author anotherAuthor = new Author();
        anotherAuthor.setUserName("Abdul");
        anotherAuthor.setProfession("Doctor");
        anotherAuthor.setEmail("abdu@gmail.com");

        log.info("Another author created --> {}", anotherAuthor);

        assertThrows(DataIntegrityViolationException.class, () -> authorRepository.save(anotherAuthor));
    }


    @Test
    void throwExceptionWhenSavingAuthorWithExistingEmailAddress(){
        log.info("Create an author --> {}", author);
        authorRepository.save(author);

        Author anotherAuthor = new Author();
        anotherAuthor.setUserName("Adex");
        anotherAuthor.setProfession("Doctor");
        anotherAuthor.setEmail("adex@gmail.com");

        log.info("Another author created --> {}", anotherAuthor);

        assertThrows(DataIntegrityViolationException.class, () -> authorRepository.save(anotherAuthor));
    }

    @Test
    void findAllAuthorInDataBaseTest(){
        List<Author> existingAuthor = authorRepository.findAll();
        assertThat(existingAuthor).isNotNull();
        assertThat(existingAuthor).hasSize(3);
    }

    @Test
    void deleteAuthorByIdTest(){
        Author savedAuthor = authorRepository.findById(20L).orElse(null);
        assertThat(savedAuthor).isNotNull();
        log.info("post fetch from the database --> {}", savedAuthor);
        authorRepository.deleteById(savedAuthor.getId());
        Author deletedAuthor = authorRepository.findById(20L).orElse(null);
        AssertionsForClassTypes.assertThat(deletedAuthor).isNull();
    }

    @Test
    void updateAuthorDetailTest(){
        Author authorToUpdate = authorRepository.findById(20L).orElse(null);
        assertThat(authorToUpdate).isNotNull();

        authorToUpdate.setProfession("Footballer");
        authorRepository.save(authorToUpdate);

        Author updatedAuthor = authorRepository.findById(20L).orElse(null);
        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getProfession()).isEqualTo("Footballer");

        log.info("Updated author --> {}", updatedAuthor);
    }

    @Test
    void validateUserWithPassword(){
        authorRepository.save(author);
        boolean isValidAuthor = authorRepository.existsByPassword("password");
        assertTrue(isValidAuthor);
    }


}