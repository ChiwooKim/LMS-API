package com.example.practice.dto;

import com.example.practice.domain.Category;
import lombok.Getter;

@Getter
public class CategoriesListResDto {

    private final Long id;
    private final String name;

    public CategoriesListResDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
