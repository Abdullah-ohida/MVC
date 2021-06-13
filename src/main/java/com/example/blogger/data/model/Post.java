package com.example.blogger.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(length = 500)
    private String postImage;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments;

    @CreationTimestamp
    private LocalDateTime datePublished;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;



    public void addComment(Comment... comment){
        if(this.comments == null)
            this.comments = new ArrayList<>();
        this.comments.addAll(Arrays.asList(comment));
    }
}
