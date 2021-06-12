package com.example.blogger.services.config;

import com.cloudinary.utils.ObjectUtils;
import com.example.blogger.web.dtos.AuthorDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class CloudinaryServiceImplTest {

    @Autowired
    CloudinaryService cloudinaryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImageMultiPartFile(){
        File file = new File("/home/whalewalker/Whalewalker/Personal/blogger/src/main/resources/static/images/author.jpg");
        assertThat(file.exists()).isTrue();

        Map<Object, Object> params = new HashMap<>();
        params.put("public_id", "blogger/post_file");
        params.put("overwrite", true);

        log.info("Image parameter --> {}", params);

        try {
            cloudinaryService.uploadImage(file, params);
        } catch (IOException e) {
            log.info("Error Occurred --> {}", e.getMessage());
        }
    }

    @Test
    void uploadMultipleImageTest() throws IOException {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setUserName("test");
        authorDto.setEmail("test@gmail.com");
        authorDto.setPassword("test123");

        Path path = Paths.get("/home/whalewalker/Whalewalker/Personal/blogger/src/main/resources/static/images/author.jpg");
        MultipartFile multipartFile = new MockMultipartFile("author.jpg", "author.jpg", "img/jpg", Files.readAllBytes(path));

        log.info("Multipart Object created --> {}", multipartFile);
        assertThat(multipartFile).isNotNull();
        authorDto.setProfileImage(multipartFile);

        log.info("File name --> {}", authorDto.getProfileImage().getOriginalFilename());
        cloudinaryService.uploadImage(multipartFile, ObjectUtils.asMap(
                "public_id", "blogger" + extractFileName(Objects.requireNonNull(authorDto.getProfileImage().getOriginalFilename()))));

    }

    private String extractFileName(String filename){
        return filename.split("\\.")[0];
    }

}