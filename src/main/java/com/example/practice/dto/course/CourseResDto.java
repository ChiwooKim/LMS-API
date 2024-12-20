package com.example.practice.dto.course;

import com.example.practice.domain.Course;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CourseResDto {

    private final Long id;
    private final String name;
    private final String description;
    private final String difficulty;
    private final String institution;
    private final float rating;
    private final String courseUrl;
    private final LocalDateTime createdDate;
    private final String category;
    private final List<String> instructors;

    public CourseResDto(Course course, List<String> instructors) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.difficulty = course.getDifficulty();
        this.institution = course.getInstitution();
        this.rating = course.getRating();
        this.courseUrl = course.getCourseUrl();
        this.category = course.getCategory().getName();
        this.createdDate = course.getCreatedDate();
        this.instructors = instructors;
    }
}
