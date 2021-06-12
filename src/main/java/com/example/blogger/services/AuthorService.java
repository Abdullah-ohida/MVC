package com.example.blogger.services;

import com.example.blogger.data.model.Author;
import com.example.blogger.web.dtos.AuthorDto;
import com.example.blogger.web.exceptions.AuthorObjectIsNullException;

import java.util.List;

public interface AuthorService {
    Author registerAuthor(AuthorDto authorDto) throws AuthorObjectIsNullException;
    AuthorDto updateAuthor(String authorId, AuthorDto authorDto);
    List<Author> getAllAuthor();
}