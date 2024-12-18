package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "instructors")
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Lob
    private String bio;

    @Column(nullable = false)
    private String profileUrl;

    @Builder
    public Instructor(String name, String bio, String profileUrl) {
        this.name = name;
        this.bio = bio;
        this.profileUrl = profileUrl;
    }
}
