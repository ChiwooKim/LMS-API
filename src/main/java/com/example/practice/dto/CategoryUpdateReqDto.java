package com.example.practice.dto;

import com.example.practice.domain.Category;
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

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
