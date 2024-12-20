package com.example.practice.repository;

import com.example.practice.domain.Course;
import com.example.practice.domain.CourseInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {

    void deleteAllByCourse(Course course);
}
