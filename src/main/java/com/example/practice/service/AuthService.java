package com.example.practice.service;

import com.example.practice.config.jwt.JwtUtil;
import com.example.practice.domain.User;
import com.example.practice.dto.user.UserCreateReqDto;
import com.example.practice.dto.user.UserReqDto;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(UserCreateReqDto reqDto) {
        // Check if the user exists
        Optional<User> userOptional = userRepository.findByEmail(reqDto.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid email");
        }

        // Password encryption
        String encryptedPassword = passwordEncoder.encode(reqDto.getPassword());

        // Create a user
        User user = User.builder()
                .username(reqDto.getUsername())
                .email(reqDto.getEmail())
                .password(encryptedPassword)
                .role("user")
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(UserReqDto reqDto) {
        // Check if the user exists
        User user = userRepository.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        // Check the password
        if (!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return jwtUtil.createJwt(user.getUsername(), user.getRole());
    }
}
