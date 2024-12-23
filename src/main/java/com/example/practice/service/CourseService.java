package com.example.practice.service;

import com.example.practice.domain.Category;
import com.example.practice.domain.Course;
import com.example.practice.domain.CourseInstructor;
import com.example.practice.domain.Instructor;
import com.example.practice.dto.course.CourseCreateReqDto;
import com.example.practice.dto.course.CourseResDto;
import com.example.practice.dto.course.CourseUpdateReqDto;
import com.example.practice.repository.CategoryRepository;
import com.example.practice.repository.CourseInstructorRepository;
import com.example.practice.repository.CourseRepository;
import com.example.practice.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final InstructorRepository instructorRepository;
    private final CourseInstructorRepository courseInstructorRepository;

    @Transactional
    public Long createCourse(CourseCreateReqDto reqDto) {
        // Fetch the Category
        Category category = categoryRepository.findByName(reqDto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Create a course
        Course course = Course.builder()
                .name(reqDto.getName())
                .description(reqDto.getDescription())
                .category(category)
                .difficulty(reqDto.getDifficulty())
                .institution(reqDto.getInstitution())
                .rating(reqDto.getRating())
                .courseUrl(reqDto.getCourseUrl())
                .createdDate(LocalDateTime.now())
                .build();
        Long createdCourseId = courseRepository.save(course).getId();

        // Create a CourseInstructor Relationships
        reqDto.getInstructors().forEach(instructorName -> {
            Instructor instructor = instructorRepository.findByName(instructorName)
                    .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
            course.addInstructor(instructor);
        });

        return createdCourseId;
    }

    @Transactional(readOnly = true)
    public List<CourseResDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseResDto(course, getInstructors(course)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResDto> getCoursesByCategory(Long categoryId) {
        // Fetch the Category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return courseRepository.findByCategory(category).stream()
                .map(course -> new CourseResDto(course, getInstructors(course)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResDto getCourseById(Long courseId) {
        // Fetch the Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Fetch the Instructors
        List<String> instructors = getInstructors(course);

        return new CourseResDto(course, instructors);
    }

    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateReqDto reqDto) {
        // Fetch the Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Fetch the Category
        Category category = categoryRepository.findByName(reqDto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Update the Course
        course.update(reqDto.getName(),
                reqDto.getDescription(),
                category,
                reqDto.getDifficulty(),
                reqDto.getInstitution(),
                reqDto.getCourseUrl());

        // Update CourseInstructor Relationships
        // Remove old instructors
        List<Instructor> currentInstructors = course.getInstructors().stream()
                .map(CourseInstructor::getInstructor)
                .toList();
        currentInstructors.forEach(course::removeInstructor);

        // Add new instructors
        reqDto.getInstructors().forEach(instructorName -> {
            Instructor instructor = instructorRepository.findByName(instructorName)
                    .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
            course.addInstructor(instructor);
        });

        return courseId;
    }

    @Transactional
    public void deleteCourse(Long id) {
        // Fetch the Course
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Remove relationships with instructors
        List<Instructor> instructors = course.getInstructors().stream()
                .map(CourseInstructor::getInstructor)
                .toList();
        instructors.forEach(course::removeInstructor);

        // Delete the course
        courseRepository.delete(course);
    }

    private static List<String> getInstructors(Course course) {
        return course.getInstructors().stream()
                .map(courseInstructor -> courseInstructor.getInstructor().getName())
                .collect(Collectors.toList());
    }
}
