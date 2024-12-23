package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "courses")
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, length = 50)
    private String difficulty;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private String courseUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseInstructor> instructors = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Course(String name, String description, Category category, String difficulty,
                  String institution, float rating, String courseUrl, LocalDateTime createdDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.institution = institution;
        this.rating = rating;
        this.courseUrl = courseUrl;
        this.createdDate = createdDate;
    }

    public void update(String name, String description, Category category,
                       String difficulty, String institution, String courseUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.institution = institution;
        this.courseUrl = courseUrl;
    }

    // 연관관계 편의 메서드
    public void addInstructor(Instructor instructor) {
        CourseInstructor courseInstructor = CourseInstructor.builder()
                .course(this)
                .instructor(instructor)
                .build();

        // 연관관계 설정
        this.instructors.add(courseInstructor);
        instructor.getCourses().add(courseInstructor);
    }

    public void removeInstructor(Instructor instructor) {
        // 연관관계 해제
        this.instructors.removeIf(courseInstructor ->
                courseInstructor.getInstructor().equals(instructor));
        instructor.getCourses().removeIf(CourseInstructor ->
                CourseInstructor.getCourse().equals(this));
    }
}
