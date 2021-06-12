package com.example.blogger.web.controllers;

import com.example.blogger.data.model.Author;
import com.example.blogger.services.AuthorService;
import com.example.blogger.web.dtos.AuthorDto;
import com.example.blogger.web.exceptions.AuthorObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/authors")
@CrossOrigin(origins = "*", maxAge = 3600)

public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAuthor(@RequestBody AuthorDto authorDto) throws AuthorObjectIsNullException {
        log.info("Register end post call");
        Author registerAuthor = authorService.registerAuthor(authorDto);
        return new ResponseEntity<>(registerAuthor, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAuthor() {
        List<Author> savedAuthors = authorService.getAllAuthor();
        return new ResponseEntity<>(savedAuthors, HttpStatus.OK);
    }

}
