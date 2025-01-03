package com.example.practice.service;

import com.example.practice.domain.Category;
import com.example.practice.dto.category.CategoriesListResDto;
import com.example.practice.dto.category.CategoryCreateReqDto;
import com.example.practice.dto.category.CategoryUpdateReqDto;
import com.example.practice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoriesListResDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoriesListResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long createCategory(CategoryCreateReqDto reqDto) {
        return categoryRepository.save(reqDto.toEntity()).getId();
    }

    @Transactional
    public Long updateCategory(Long id, CategoryUpdateReqDto reqDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category is not found. id=" + id));

        category.update(reqDto.getName());
        return category.getId();
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category is not found. id=" + id));

        categoryRepository.delete(category);
    }
}
