package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // 사용자 역할 (USER, ADMIN)

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public User(String username, String email, String password, String role, LocalDateTime createdDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
    }
}
