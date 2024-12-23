package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "courses_instructors")
@Entity
public class CourseInstructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Builder
    public CourseInstructor(Course course, Instructor instructor) {
        this.course = course;
        this.instructor = instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseInstructor that = (CourseInstructor) o;
        return Objects.equals(course, that.course) && Objects.equals(instructor, that.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, instructor);
    }
}
