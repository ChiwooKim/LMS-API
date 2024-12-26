package com.example.practice.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReqDto {

    @NotBlank(message = "Please enter your email")
    private String email;
    @NotBlank(message = "Please enter your password")
    private String password;

    @Builder
    public UserReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
