package com.example.blogger.services;

import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import com.example.blogger.web.dtos.PostDto;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto);
    List<Post> findAllPosts();
    Post updatePost(PostDto postDto);
    Post findPostById(Long id);
    void deletePostById(Long id);
    Post addCommentToPost(Long id, Comment comment);
}
