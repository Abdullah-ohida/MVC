package com.example.blogger.services;

import com.example.blogger.data.model.Author;
import com.example.blogger.data.repositories.AuthorRepository;
import com.example.blogger.web.dtos.AuthorDto;
import com.example.blogger.web.exceptions.AuthorObjectIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorServiceImpl;

    AuthorDto author;

    Author testAuthor;

    @BeforeEach
    void setUp() {
        testAuthor = new Author();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenTheSaveMethodIsCalled_ThenRepositoryIsCalledOnce() throws AuthorObjectIsNullException {
        when(authorServiceImpl.registerAuthor(new AuthorDto())).thenReturn(testAuthor);
        authorServiceImpl.registerAuthor(new AuthorDto());

        verify(authorRepository, times(1)).save(testAuthor);
    }
}