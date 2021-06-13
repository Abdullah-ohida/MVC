package com.example.blogger.services;

import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import com.example.blogger.web.dtos.PostDto;
import com.example.blogger.web.exceptions.CommentNullException;
import com.example.blogger.web.exceptions.PostDoesNotExistException;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto);
    List<Post> findAllPosts();
    List<Post> findAllPostByAuthorId(Long id) throws PostDoesNotExistException;
    void deleteAllPostByAuthorId(Long id) throws PostDoesNotExistException;
    Post updatePost(Long id, PostDto postDto) throws PostDoesNotExistException;
    Post findPostById(Long id) throws PostDoesNotExistException;
    void deletePostById(Long id);
    Post addCommentToPost(Long id, Comment comment) throws PostDoesNotExistException, CommentNullException;
}
