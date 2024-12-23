package com.example.practice.repository;

import com.example.practice.domain.Course;
import com.example.practice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCourse(Course course);
}
