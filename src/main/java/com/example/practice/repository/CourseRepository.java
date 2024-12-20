package com.example.practice.repository;

import com.example.practice.domain.Category;
import com.example.practice.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategory(Category category);
}
