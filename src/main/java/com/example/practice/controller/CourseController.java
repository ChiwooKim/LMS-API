package com.example.practice.controller;

import com.example.practice.dto.course.CourseCreateReqDto;
import com.example.practice.dto.course.CourseResDto;
import com.example.practice.dto.course.CourseUpdateReqDto;
import com.example.practice.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Long createCourse(@RequestBody @Valid CourseCreateReqDto requestDto) {
        return courseService.createCourse(requestDto);
    }

    @GetMapping
    public List<CourseResDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{categoryId}")
    public List<CourseResDto> getCoursesByCategory(@PathVariable Long categoryId) {
        return courseService.getCoursesByCategory(categoryId);
    }

    @GetMapping("/{courseId}")
    public CourseResDto getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public Long updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateReqDto requestDto) {
        return courseService.updateCourse(courseId, requestDto);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }
}
