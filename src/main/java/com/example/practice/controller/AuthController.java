package com.example.practice.controller;

import com.example.practice.dto.user.UserCreateReqDto;
import com.example.practice.dto.user.UserReqDto;
import com.example.practice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserCreateReqDto reqDto) {
        authService.signup(reqDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid UserReqDto reqDto) {
        return authService.login(reqDto);
    }
}
