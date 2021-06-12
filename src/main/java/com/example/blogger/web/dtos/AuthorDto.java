package com.example.blogger.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    @NotNull(message = "Username cannot be null")
    @NonNull
    private String userName;

    private String profession;

    private MultipartFile profileImage;

    @NonNull
    @NotNull(message = "Password cannot be null")
    private String password;

    @NonNull
    @Email(message = "Email cannot be null")
    private String email;
}
