package com.example.practice.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryUpdateReqDto {

    @NotBlank(message = "Please enter a category")
    private String name;

    @Builder
    public CategoryUpdateReqDto(String name) {
        this.name = name;
    }
}
