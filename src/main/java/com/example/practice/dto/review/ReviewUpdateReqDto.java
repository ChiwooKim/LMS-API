package com.example.practice.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateReqDto {

    private Long reviewId;
    private Float rating;
    private String comment;

    @Builder
    public ReviewUpdateReqDto(Long reviewId, Float rating, String comment) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
    }
}
