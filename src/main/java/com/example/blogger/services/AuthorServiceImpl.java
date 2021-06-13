package com.example.blogger.services;

import com.cloudinary.utils.ObjectUtils;
import com.example.blogger.data.model.Author;
import com.example.blogger.data.repositories.AuthorRepository;
import com.example.blogger.services.config.CloudinaryService;
import com.example.blogger.web.dtos.AuthorDto;
import com.example.blogger.web.exceptions.AuthorObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public Author registerAuthor(AuthorDto authorDto) throws AuthorObjectIsNullException {
        if(authorDto == null) throw new AuthorObjectIsNullException("Author cannot be null");

        Author author = new Author();
        if(authorDto.getProfileImage() != null && !authorDto.getProfileImage().isEmpty()){
            try {
                Map<?, ?> uploadResult = cloudinaryService.uploadImage(authorDto.getProfileImage(), ObjectUtils.asMap("public_id", "blogger/" + extractFileName(authorDto.getProfileImage().getName())));
                author.setProfileImage(String.valueOf(uploadResult.get("url")));
            } catch (Exception e) {
                log.info("Error occurred while uploading image to cloudinary --> {}", e.getMessage());
            }
        }

        author.setUserName(authorDto.getUserName());
        author.setPassword(authorDto.getPassword());
        author.setEmail(authorDto.getEmail());
        author.setProfession(authorDto.getProfession());

        return authorRepository.save(author);
    }

    private String extractFileName(String filename) {
        return filename.split("\\.")[0];
    }

    @Override
    public AuthorDto updateAuthor(String authorId, AuthorDto authorDto) {
        return null;
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }
}
