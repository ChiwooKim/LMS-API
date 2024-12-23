package com.example.practice.service;

import com.example.practice.domain.Category;
import com.example.practice.domain.Course;
import com.example.practice.domain.Instructor;
import com.example.practice.dto.course.CourseCreateReqDto;
import com.example.practice.dto.course.CourseResDto;
import com.example.practice.dto.course.CourseUpdateReqDto;
import com.example.practice.repository.CategoryRepository;
import com.example.practice.repository.CourseInstructorRepository;
import com.example.practice.repository.CourseRepository;
import com.example.practice.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseInstructorRepository courseInstructorRepository;

    private Category testCategory;
    private Instructor testInstructor;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        instructorRepository.deleteAll();
        courseInstructorRepository.deleteAll();

        testCategory = Category.builder()
                .name("Programming")
                .build();
        categoryRepository.save(testCategory);

        testInstructor = Instructor.builder()
                .name("김영한")
                .bio("진짜 실무에 필요한 제대로 된 개발자가 될 수 있도록, 교육하는 것이 저의 목표입니다.")
                .profileUrl("없음")
                .build();
        instructorRepository.save(testInstructor);
    }

    @Test
    @Transactional
    void createCourse() {
        // Given
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

        // When
        Long courseId = courseService.createCourse(reqDto);

        // Then
        Course savedCourse = courseRepository.findById(courseId).orElseThrow();
        assertThat(savedCourse.getName()).isEqualTo("자바 ORM 표준 JPA 프로그래밍 - 기본편");
        assertThat(savedCourse.getCategory().getName()).isEqualTo("Programming");
        assertThat(savedCourse.getInstructors().getFirst().getInstructor().getName()).isEqualTo("김영한");
        assertThat(savedCourse.getInstructors().size()).isEqualTo(1);
    }

    @Test
    void getAllCourses() {
        // Given
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
        courseService.createCourse(reqDto);

        // When
        List<CourseResDto> courses = courseService.getAllCourses();

        // Then
        assertThat(courses.size()).isEqualTo(1);
        assertThat(courses.getFirst().getName()).isEqualTo("자바 ORM 표준 JPA 프로그래밍 - 기본편");
    }

    @Test
    void getCoursesByCategory() {
        // Given
        CourseCreateReqDto reqDto = CourseCreateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 기본편")
                .description("초급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Beginner")
                .institution("inflearn")
                .rating(5.0f)
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();
        courseService.createCourse(reqDto);

        // When
        List<CourseResDto> courses = courseService.getCoursesByCategory(testCategory.getId());

        // Then
        assertThat(courses.size()).isEqualTo(1);
        assertThat(courses.getFirst().getName()).isEqualTo("자바 ORM 표준 JPA 프로그래밍 - 기본편");
    }

    @Test
    void getCourseById() {
        // Given
        CourseCreateReqDto reqDto = CourseCreateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 기본편")
                .description("초급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Beginner")
                .institution("inflearn")
                .rating(5.0f)
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();
        Long courseId = courseService.createCourse(reqDto);

        // When
        CourseResDto course = courseService.getCourseById(courseId);

        // Then
        assertThat(course.getName()).isEqualTo("자바 ORM 표준 JPA 프로그래밍 - 기본편");
        assertThat(course.getCategory()).isEqualTo("Programming");
        assertThat(course.getInstructors().contains("김영한")).isTrue();
    }

    @Test
    void updateCourse() {
        // Given
        CourseCreateReqDto reqDto = CourseCreateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 기본편")
                .description("초급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Beginner")
                .institution("inflearn")
                .rating(5.0f)
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();
        Long courseId = courseService.createCourse(reqDto);

        CourseUpdateReqDto updateReqDto = CourseUpdateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 고급편")
                .description("상급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Advanced")
                .institution("inflearn")
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();

        // When
        courseService.updateCourse(courseId, updateReqDto);

        // Then
        Course updatedCourse = courseRepository.findById(courseId).orElseThrow();
        assertThat(updatedCourse.getName()).isEqualTo("자바 ORM 표준 JPA 프로그래밍 - 고급편");
        assertThat(updatedCourse.getDifficulty()).isNotEqualTo("Beginner");
        assertThat(updatedCourse.getInstitution()).isEqualTo("inflearn");
    }

    @Test
    void deleteCourse() {
        // Given
        CourseCreateReqDto reqDto = CourseCreateReqDto.builder()
                .name("자바 ORM 표준 JPA 프로그래밍 - 기본편")
                .description("초급자를 위해 준비한\n" +
                        "[웹 개발, 백엔드] 강의입니다.")
                .category("Programming")
                .difficulty("Beginner")
                .institution("inflearn")
                .rating(5.0f)
                .courseUrl("https://www.inflearn.com/course/ORM-JPA-Basic")
                .instructors(List.of("김영한"))
                .build();
        Long courseId = courseService.createCourse(reqDto);

        // When
        courseService.deleteCourse(courseId);

        // Then
        assertThat(courseRepository.findById(courseId)).isEmpty();
    }
}