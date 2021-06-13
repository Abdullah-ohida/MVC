package com.example.blogger.web.controllers;

import com.example.blogger.data.model.Author;
import com.example.blogger.services.AuthorService;
import com.example.blogger.web.dtos.AuthorDto;
import com.example.blogger.web.exceptions.AuthorObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/authors", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", maxAge = 3600)

public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping(value="/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registerAuthor(AuthorDto authorDto) throws AuthorObjectIsNullException {
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
