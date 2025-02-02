package com.example.practice.service;

import com.example.practice.domain.Course;
import com.example.practice.domain.Review;
import com.example.practice.domain.User;
import com.example.practice.dto.review.ReviewCreateReqDto;
import com.example.practice.dto.review.ReviewResDto;
import com.example.practice.dto.review.ReviewUpdateReqDto;
import com.example.practice.repository.CourseRepository;
import com.example.practice.repository.ReviewRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createReview(ReviewCreateReqDto reqDto, Long userId) {
        // Fetch the course & user
        Course course = courseRepository.findById(reqDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a review
        Review review = Review.builder()
                .course(course)
                .user(user)
                .rating(reqDto.getRating())
                .comment(reqDto.getComment())
                .createdDate(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        updateCourseRating(course);

        return review.getId();
    }

    @Transactional(readOnly = true)
    public List<ReviewResDto> getReviewsByCourse(Long courseId) {
        // Fetch the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        return reviewRepository.findByCourse(course).stream()
                .map(ReviewResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResDto getReviewById(Long reviewId) {
        return new ReviewResDto(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found")));
    }

    @Transactional
    public Long updateReview(Long reviewId, ReviewUpdateReqDto reqDto) {
        // Fetch the review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        // Update the review
        review.updateReview(reqDto.getRating(), reqDto.getComment());
        updateCourseRating(review.getCourse());

        return reviewId;
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        // Delete the review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        Course course = review.getCourse();
        reviewRepository.delete(review);
        updateCourseRating(course);
    }

    private void updateCourseRating(Course course) {
        List<Review> reviews = reviewRepository.findByCourse(course);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0);
        course.updateRating((float) averageRating);
    }
}
