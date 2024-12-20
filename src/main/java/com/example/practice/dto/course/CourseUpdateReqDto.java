package com.example.practice.dto.course;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourseUpdateReqDto {

    private String name;
    private String description;
    private String difficulty;
    private String institution;
    private String courseUrl;
    private String category;
    private List<String> instructors;

    @Builder
    public CourseUpdateReqDto(String name, String description, String difficulty, String institution,
                              String courseUrl, String category, List<String> instructors) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.institution = institution;
        this.courseUrl = courseUrl;
        this.category = category;
        this.instructors = instructors;
    }
}
