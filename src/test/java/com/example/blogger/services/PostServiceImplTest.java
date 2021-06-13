package com.example.blogger.services;

import com.example.blogger.data.model.Post;
import com.example.blogger.data.repositories.PostRepository;
import com.example.blogger.web.dtos.PostDto;
import com.example.blogger.web.exceptions.PostDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    PostRepository postRepository;


    @InjectMocks
    PostServiceImpl postService;

    Post postTest;

    @BeforeEach
    void setUp() {
        postTest = new Post();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenTheSaveMethodISCalled_ThenRepositoryISCalled(){
        when(postService.createPost(new PostDto())).thenReturn(postTest);
        postService.createPost(new PostDto());

        verify(postRepository, times(1)).save(postTest);
    }

    @Test
    void whenTheFindAllMethodIsCalled_thenReturnAListOfPost(){
        List<Post> postList = new ArrayList<>();

        when(postService.findAllPosts()).thenReturn(postList);

        postService.findAllPosts();

        verify(postRepository, times(1)).findByOrderByDatePublishedDesc();
    }

    @Test
    void whenTheFindByIdMethodIsCalled_thenReturnASpecificPostWithThatId() throws PostDoesNotExistException {

       postService.findPostById(1L);

       verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void whenTheDeleteByIdMethodIsInvoke_theDeletePostFromDbTest(){
        Long id = 1L;

        postService.deletePostById(1L);

        verify(postRepository, times(1)).deleteById(id);
    }
}