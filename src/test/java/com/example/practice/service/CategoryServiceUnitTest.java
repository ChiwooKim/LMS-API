package com.example.practice.service;

import com.example.practice.domain.Category;
import com.example.practice.dto.CategoriesListResDto;
import com.example.practice.dto.CategoryCreateReqDto;
import com.example.practice.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 프레임워크와 JUnit을 통합하여 테스트 실행
class CategoryServiceUnitTest {

    @InjectMocks
    private CategoryService categoryService; // Mock 객체가 주입된 CategoryService 인스턴스 생성

    @Mock
    private CategoryRepository categoryRepository; // CategoryRepository를 Mock 객체로 생성

    @Test
    void testGetAllCategories() {
        // Given
        List<Category> mockCategories = List.of(
                Category.builder().name("Programming").build(),
                Category.builder().name("Data Science").build());
        when(categoryRepository.findAll()).thenReturn(mockCategories); // Mock 객체의 동작 정의

        // When
        List<CategoriesListResDto> categories = categoryService.getAllCategories();

        // Then
        assertEquals(2, categories.size()); // 예상 결과와 실제 결과 비교
        verify(categoryRepository, times(1)).findAll(); // 특정 메서드 호출 횟수 검증
    }

    @Test
    void testCreateCategory() {
        // Given
        CategoryCreateReqDto categoryDto = CategoryCreateReqDto.builder().name("AI").build();
        Category mockCategory = Category.builder().name("AI").build();
        ReflectionTestUtils.setField(mockCategory, "id", 1L);
        // Mock 객체의 동작 정의
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        // When
        Long categoryId = categoryService.createCategory(categoryDto);
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category is not found. id=" + categoryId));

        // Then
        assertNotNull(savedCategory); // 객체가 null이 아님을 확인
        assertEquals("AI", savedCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}