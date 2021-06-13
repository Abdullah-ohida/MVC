package com.example.blogger.web.dtos;

import com.example.blogger.data.model.Post;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Content cannot be empty")
    private String content;

    private MultipartFile imageFile;

}
