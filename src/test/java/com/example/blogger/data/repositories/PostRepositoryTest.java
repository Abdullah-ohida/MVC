package com.example.blogger.data.repositories;

import com.example.blogger.data.model.Author;
import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Sql(scripts = "classpath:db/insert.sql")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    Post blogPost;
    Author author;
    Comment comment;

    @BeforeEach
    void setUp() {
        blogPost = new Post();
        author = new Author();
        comment = new Comment();

        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Financial technology is the technology " +
                "and innovation that aims to compete with traditional financial methods" +
                " in the delivery of financial services. It is an" +
                " emerging industry that uses technology to improve activities in finance.");

        author.setUserName("adex");
        author.setEmail("adex@gmail.com");

        comment.setCommentatorName("Whalewalker");
        comment.setContent("Cool stuff, thanks for sharing");
    }

    @AfterEach
    void tearDown(){
        blogPost =  null;
        comment = null;
        author = null;
    }


    @Test
    @Rollback(value = false)
    void savePostToDataBaseTest(){
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);

        Post savedPost = postRepository.findPostByTitle(blogPost.getTitle());
        assertThat(savedPost.getTitle()).isNotNull();

        log.info("Saved Post --> {}", savedPost);
    }

    @Test
    void throwExceptionWhenSavingAPostWithExistingTitle(){
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);

        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Financial technology is the technology " +
                "and innovation that aims to compete with traditional financial methods" +
                " in the delivery of financial services. It is an" +
                " emerging industry that uses technology to improve activities in finance.");

        log.info("Created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));
    }

    @Test
    void whenPostIsCreateAuthorIsCreatedTest(){
        log.info("Created a blog post --> {}", blogPost);
//        Map relation  ship
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        Post savedPost = postRepository.findPostByTitle("What is Fintech?");
        assertThat(savedPost.getTitle()).isNotNull();
        log.info("Blog post after saving to Db--> {}", blogPost);

        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getUserName()).isEqualTo("adex");
    }

    @Test
    void findAllPostSInDatabaseTest(){
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(3);
    }

    @Test
    @Rollback(value = false)
    void findAllPostByUserLastName(){
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is computer?");
        blogPost2.setContent("A computer is a machine that can be programmed to carry out sequences of arithmetic or logical operations automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks");
        blogPost2.setAuthor(author);
        author.addPost(blogPost2);
        log.info("Created a blog post --> {}", blogPost2);
        postRepository.save(blogPost2);

        List<Post> authorPosts = postRepository.findAllPostByAuthorUserName(author.getUserName());
        assertThat(authorPosts).isNotNull();
        assertThat(authorPosts).hasSize(2);
    }

    @Test
    void deletePostTest(){
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
        Post savedPost = postRepository.findPostByTitle("What is Fintech?");
        postRepository.deleteById(savedPost.getId());
        Post deletedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    void getPostByIdTest(){
        Post getPost = postRepository.findById(42L).orElse(null);
        assertThat(getPost).isNotNull();

        log.info("get post from the database --> {}", getPost);
    }

    @Test
    void updateSavedPostTest(){
        Post postToUpdate = postRepository.findById(43L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getTitle()).isEqualTo("Title post 3");
        log.info("post fetch from the database --> {}", postToUpdate);

        postToUpdate.setTitle("What is Hatred?");
        postToUpdate.setContent("Hatred is a relatively stable feeling of intense dislike for another person, entity, or group");
        postRepository.save(postToUpdate);

        Post updatedPost = postRepository.findById(43L).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("What is Hatred?");
        log.info("Updated post fetch from database --> {}", updatedPost);
    }

    @Test
    void updateAuthorDetailTest(){
        Post postToUpdate = postRepository.findById(41L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getAuthor()).isNull();

        postToUpdate.setAuthor(author);
        postRepository.save(postToUpdate);

        Post updatedPost = postRepository.findById(41L).orElse(null);
        assertThat(updatedPost).isNotNull();

        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getUserName()).isEqualTo("adex");
    }

    @Test
    void updateCommentPostTest(){
        Post postToUpdate = postRepository.findById(41L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getComments()).isEmpty();

        postToUpdate.addComment(comment);
        postRepository.save(postToUpdate);

        Post updatedPost = postRepository.findById(41L).orElse(null);
        assertThat(updatedPost).isNotNull();

        assertThat(updatedPost.getComments()).isNotNull();
        assertThat(updatedPost.getComments().get(0).getCommentatorName()).isEqualTo("Whalewalker");
    }

    @Test
    @Rollback(value = false)
    void getAllPostInDescendingOrder(){
        List<Post> getAllPosts = postRepository.findByOrderByDatePublishedDesc();
        assertTrue(getAllPosts.get(0).getDatePublished().isAfter(getAllPosts.get(1).getDatePublished()));

        for (Post post : getAllPosts) {
            log.info("Post Data --> {}", post);
        }
    }

//    @Test
//    @Rollback(value = false)
//    void getAllPostCommentInDescendingOrder(){
//        Post postToUpdate = postRepository.findById(41L).orElse(null);
//        assertThat(postToUpdate).isNotNull();
//        assertThat(postToUpdate.getComments()).isEmpty();
//
//        postToUpdate.addComment(comment);
//        postRepository.save(postToUpdate);
//
//        Post blogPost2 = new Post();
//        blogPost2.setTitle("What is computer?");
//        blogPost2.setContent("A computer is a machine that can be programmed to carry out sequences of arithmetic or logical operations automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks");
//        blogPost2.setAuthor(author);
//        author.addPost(blogPost2);
//        log.info("Created a blog post --> {}", blogPost2);
//
//        Comment comment1 = new Comment();
//
//        comment1.setCommentatorName("Cool");
//        comment.setContent("Cool stuff, Amazing");
//
//        postRepository.save(blogPost2);
//
//
//        List<Comment> getAllComment= commentRepository.findByOrderByDataCreatedDesc();
//        assertThat(getAllComment.get(0).getDataCreated().isAfter((getAllComment.get(1).getDataCreated())));
//
//        for (Comment comment : getAllComment) {
//            log.info("Post Data --> {}", comment);
//        }
//    }
}

