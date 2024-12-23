package com.example.practice.controller;

import com.example.practice.dto.review.ReviewCreateReqDto;
import com.example.practice.dto.review.ReviewResDto;
import com.example.practice.dto.review.ReviewUpdateReqDto;
import com.example.practice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Long createReview(@RequestBody @Valid ReviewCreateReqDto reqDto) {
        return reviewService.createReview(reqDto);
    }

    @GetMapping("/{courseId}")
    public List<ReviewResDto> getReviewsByCourse(@PathVariable Long courseId) {
        return reviewService.getReviewsByCourse(courseId);
    }

    @GetMapping("/{reviewId}")
    public ReviewResDto getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @PutMapping("/{reviewId}")
    public Long updateReview(@PathVariable Long reviewId, @RequestBody @Valid ReviewUpdateReqDto reqDto) {
        return reviewService.updateReview(reviewId, reqDto);

    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
