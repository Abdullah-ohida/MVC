package com.example.blogger.services.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    void uploadImage(File file, Map<?, ?> imageProperties) throws IOException;
    Map<?, ?> uploadImage(MultipartFile file, Map<?, ?> imageProperties) throws IOException;
}
