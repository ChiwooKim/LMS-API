package com.example.practice.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourseCreateReqDto {

    @NotBlank(message = "Please enter the course name")
    private String name;

    @NotBlank(message = "Please enter a course description")
    private String description;

    @NotBlank(message = "The difficulty level should be beginner, intermediate, or advanced")
    private String difficulty;

    @NotBlank(message = "Please enter the institution providing the course")
    private String institution;

    @NotBlank(message = "When registering for a course, your initial rating must be 0")
    private Float rating;

    @NotBlank(message = "Please enter the courseUrl")
    private String courseUrl;

    @NotBlank(message = "Please enter a category")
    private String category;

    @NotBlank(message = "Please enter the instructor name for the course")
    private List<String> instructors;

    @Builder
    public CourseCreateReqDto(String name, String description, String difficulty, String institution,
                              Float rating, String courseUrl, String category, List<String> instructors) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.institution = institution;
        this.rating = rating;
        this.courseUrl = courseUrl;
        this.category = category;
        this.instructors = instructors;
    }
}
