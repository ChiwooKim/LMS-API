package com.example.practice.controller;

import com.example.practice.dto.category.CategoriesListResDto;
import com.example.practice.dto.category.CategoryCreateReqDto;
import com.example.practice.dto.category.CategoryUpdateReqDto;
import com.example.practice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoriesListResDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public Long createCategory(@RequestBody @Valid CategoryCreateReqDto reqDto) {
        return categoryService.createCategory(reqDto);
    }

    @PutMapping("/{id}")
    public Long updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateReqDto reqDto) {
        return categoryService.updateCategory(id, reqDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return id;
    }
}
