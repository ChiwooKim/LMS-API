package com.example.practice.dto;

import com.example.practice.domain.Category;
import lombok.Getter;

@Getter
public class CategoriesListResDto {

    private Long id;
    private String name;

    public CategoriesListResDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
