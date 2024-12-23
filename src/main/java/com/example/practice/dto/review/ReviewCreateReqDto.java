package com.example.practice.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateReqDto {

    @NotBlank
    private Long courseId;

    @NotBlank(message = "Please enter your rating")
    private Float rating;

    @NotBlank(message = "Please enter details")
    private String comment;

    @Builder
    public ReviewCreateReqDto(Long courseId, Float rating, String comment) {
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
    }
}
