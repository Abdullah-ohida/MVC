package com.example.blogger.data.repositories;

import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostByTitle(String title);
    List<Post> findAllPostByAuthorUserName(String lastName);
    List<Post> findByOrderByDatePublishedDesc();
}
