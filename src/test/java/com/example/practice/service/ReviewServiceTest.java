package com.example.practice.service;

import com.example.practice.domain.*;
import com.example.practice.dto.course.CourseCreateReqDto;
import com.example.practice.dto.review.ReviewCreateReqDto;
import com.example.practice.dto.review.ReviewResDto;
import com.example.practice.dto.review.ReviewUpdateReqDto;
import com.example.practice.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserRepository userRepository;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        // Initializing the test environment
        reviewRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        instructorRepository.deleteAll();
        userRepository.deleteAll();

        // Generate test data
        Category testCategory = Category.builder().name("Programming").build();
        categoryRepository.save(testCategory);

        Instructor testInstructor = Instructor.builder()
                .name("김영한")
                .bio("진짜 실무에 필요한 제대로 된 개발자가 될 수 있도록, 교육하는 것이 저의 목표입니다.")
                .profileUrl("없음")
                .build();
        instructorRepository.save(testInstructor);

        CourseCreateReqDto reqDto = CourseCreateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 기본편")
                .description("초급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Beginner")
                .institution("inflearn")
                .rating(0f)
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();
        Long courseId = courseService.createCourse(reqDto);
        testCourse = courseRepository.findById(courseId).orElseThrow();
    }

    @Test
    void createReview() {
        // Given
        ReviewCreateReqDto reqDto = ReviewCreateReqDto.builder()
                .courseId(testCourse.getId())
                .rating(4.0f)
                .comment("이해하기 쉽게 설명해줘요!")
                .build();
        User user = User.builder()
                .username("testUser")
                .email("testmail@gmail.com")
                .password("123456789")
                .role("user")
                .createdDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        // When
        Long reviewId = reviewService.createReview(reqDto, user.getId());

        // Then
        Review savedReview = reviewRepository.findById(reviewId).orElseThrow();
        Course updatedCourse = courseRepository.findById(testCourse.getId()).orElseThrow();

        assertThat(updatedCourse.getRating()).isEqualTo(4.0f);
        assertThat(savedReview.getRating()).isEqualTo(4.0f);
        assertThat(savedReview.getComment()).isEqualTo("이해하기 쉽게 설명해줘요!");
    }

    @Test
    void getReviewsByCourse() {
        // Given
        Review review = Review.builder()
                .course(testCourse)
                .rating(5.0f)
                .comment("Good course")
                .createdDate(LocalDateTime.now())
                .build();
        reviewRepository.save(review);

        // When
        List<ReviewResDto> reviews = reviewService.getReviewsByCourse(testCourse.getId());

        // Then
        assertThat(reviews).hasSize(1);
        assertThat(reviews.getFirst().getRating()).isEqualTo(5.0f);
        assertThat(reviews.getFirst().getComment()).isEqualTo("Good course");
    }

    @Test
    void getReviewById() {
        // Given
        Review review = Review.builder()
                .course(testCourse)
                .rating(5.0f)
                .comment("Good course")
                .createdDate(LocalDateTime.now())
                .build();
        reviewRepository.save(review);

        // When
        ReviewResDto resReview = reviewService.getReviewById(review.getId());

        // Then
        assertThat(resReview.getRating()).isEqualTo(5.0f);
        assertThat(resReview.getComment()).isEqualTo("Good course");
    }

    @Test
    void updateReview() {
        // Given
        Review review = Review.builder()
                .course(testCourse)
                .rating(4.0f)
                .comment("Good course")
                .createdDate(java.time.LocalDateTime.now())
                .build();
        reviewRepository.save(review);

        ReviewUpdateReqDto reqDto = ReviewUpdateReqDto.builder()
                .rating(5.0f)
                .comment("Excellent course")
                .build();

        // When
        reviewService.updateReview(review.getId(), reqDto);

        // Then
        Review updatedReview = reviewRepository.findById(review.getId()).orElseThrow();
        Course updatedCourse = courseRepository.findById(testCourse.getId()).orElseThrow();

        assertThat(updatedCourse.getRating()).isEqualTo(5.0f);
        assertThat(updatedReview.getRating()).isEqualTo(5.0f);
        assertThat(updatedReview.getComment()).isEqualTo("Excellent course");
    }

    @Test
    void deleteReview() {
        // Given
        Review review1 = Review.builder()
                .course(testCourse)
                .rating(4.0f)
                .comment("Good course")
                .createdDate(LocalDateTime.now())
                .build();
        reviewRepository.save(review1);

        Review review2 = Review.builder()
                .course(testCourse)
                .rating(5.0f)
                .comment("Excellent course")
                .createdDate(LocalDateTime.now())
                .build();
        reviewRepository.save(review2);

        // When
        reviewService.deleteReview(review1.getId());

        // Then
        Course updatedCourse = courseRepository.findById(testCourse.getId()).orElseThrow();

        assertThat(updatedCourse.getRating()).isEqualTo(5.0f);
        assertThat(reviewRepository.findById(review1.getId())).isEmpty();
    }
}