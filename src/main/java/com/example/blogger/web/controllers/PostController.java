package com.example.blogger.web.controllers;

import com.example.blogger.data.model.Post;
import com.example.blogger.services.PostService;
import com.example.blogger.web.dtos.PostDto;
import com.example.blogger.web.exceptions.PostDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value="api/v1/posts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", maxAge = 3600)

public class PostController {

    final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        log.info("PostDto --> {}", postDto);
        Post post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        log.info("id --> {}", id);
        try {
            Post post = postService.findPostById(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updatePostById(@PathVariable Long id, @RequestBody PostDto updatePostDto) {
        try {
            Post updated = postService.updatePost(id, updatePostDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        log.info("id --> {}", id);
        postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("author/posts/{id}")
    public ResponseEntity<?> getAllAuthorPosts(@PathVariable Long id) {
        try {
            List<Post> authorPosts = postService.findAllPostByAuthorId(id);
            return new ResponseEntity<>(authorPosts, HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @DeleteMapping("{authorId}")
    public ResponseEntity<?> deleteAllPostsByAuthorId(@PathVariable Long authorId) {
        log.info("authorId --> {}", authorId);
        try {
            postService.deleteAllPostByAuthorId(authorId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
