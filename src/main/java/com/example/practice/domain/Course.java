package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "courses")
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private String courseUrl;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public Course(String name, String description, String category, String difficulty, String institution,
                  float rating, String courseUrl, LocalDateTime createdDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.institution = institution;
        this.rating = rating;
        this.courseUrl = courseUrl;
        this.createdDate = createdDate;
    }
}
