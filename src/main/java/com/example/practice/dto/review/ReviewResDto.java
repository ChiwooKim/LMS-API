package com.example.practice.dto.review;

import com.example.practice.domain.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResDto {

    private final Long id;
    private final Float rating;
    private final String comment;
    private final LocalDateTime createdDate;

    public ReviewResDto(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdDate = getCreatedDate();
    }
}
