package com.example.practice.dto.category;

import com.example.practice.domain.Category;
import lombok.Getter;

@Getter
public class CategoriesListResDto {

    private final Long id;
    private final String name;

    public CategoriesListResDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
