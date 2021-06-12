package com.example.blogger.data.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    private String profession;

    private String profileImage;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Post> posts;

    public void addPost(Post post){
        if(posts == null)
            posts = new ArrayList<>();
        posts.add(post);
    }

}
