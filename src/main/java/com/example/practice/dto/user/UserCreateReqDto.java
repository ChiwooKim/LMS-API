package com.example.practice.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateReqDto {

    @NotBlank(message = "Please enter your username")
    private String username;
    @NotBlank(message = "Please enter your email")
    private String email;
    @NotBlank(message = "Please enter your password")
    private String password;

    @Builder
    public UserCreateReqDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
