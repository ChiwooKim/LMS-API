package com.example.practice.service;

import com.example.practice.domain.Category;
import com.example.practice.dto.category.CategoryCreateReqDto;
import com.example.practice.dto.category.CategoryUpdateReqDto;
import com.example.practice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void clean() {
        categoryRepository.deleteAll();
    }

    @Test
    void testUpdateCategory() {
        // Given
        CategoryCreateReqDto categoryDto = CategoryCreateReqDto.builder().name("Old Name").build();
        Category category = categoryRepository.save(categoryDto.toEntity());
        CategoryUpdateReqDto updateCategoryDto = CategoryUpdateReqDto.builder().name("Young Name").build();

        // When
        categoryService.updateCategory(category.getId(), updateCategoryDto);

        // Then
        Category updatedCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category is not found. id=" + category.getId()));
        assertEquals("Young Name", updatedCategory.getName());
    }

    @Test
    void testDeleteCategory() {
        // Given
        CategoryCreateReqDto categoryDto = CategoryCreateReqDto.builder().name("Deleted category").build();
        Category category = categoryRepository.save(categoryDto.toEntity());
        Long categoryId = category.getId();

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        Optional<Category> deletedCategory = categoryRepository.findById(categoryId);
        assertTrue(deletedCategory.isEmpty());
    }

}