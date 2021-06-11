package com.example.blogger.data.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(length = 500)
    private String postImage;

    @Column()
    private Author author;

    private List<Comment> comments;

    private List<Like> likes;

    @CreationTimestamp
    private LocalDateTime datePublished;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;
}
