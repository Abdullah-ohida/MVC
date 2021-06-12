package com.example.blogger.services;

import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import com.example.blogger.web.dtos.PostDto;

import java.util.List;

public class PostServiceImpl implements PostService{

    @Override
    public Post createPost(PostDto postDto) {
        return null;
    }

    @Override
    public List<Post> findAllPosts() {
        return null;
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findPostById(Long id) {
        return null;
    }

    @Override
    public void deletePostById(Long id) {

    }

    @Override
    public Post addCommentToPost(Long id, Comment comment) {
        return null;
    }
}
